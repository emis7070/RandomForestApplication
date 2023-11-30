package PlatinumMindSoftwares.RandomForestApplication;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;


@SpringBootApplication
public class RandomForestApplication {

	public static void main(String[] args) {
		String filePath = "src/main/java/PlatinumMindSoftwares/RandomForestApplication/datasets/Dataset_spine.csv";
		int labelIndex = 12; // Assuming the target value is in the last column

		// Read dataset from CSV
		Dataset dataset = readDatasetFromCSV(filePath, labelIndex);

		// Display some information about the dataset
		System.out.println("Number of instances: " + dataset.getNumOfInstances());
		System.out.println("Number of features: " + dataset.getNumOfFeatures());

		// Print the first few instances
		System.out.println("First few instances:");
		for (int i = 0; i < Math.min(5, dataset.getSize()); i++) {
			Instance instance = dataset.getInstance(i);
			System.out.println("Instance " + (i + 1) + ": " + instance);
		}

	}
	private static Dataset readDatasetFromCSV(String filePath, int labelIndex) {
		List<Instance> instances = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> allData = reader.readAll();

			// Skip the header line if it exists
			allData.remove(0);

			for (String[] values : allData) {
				if (values.length >= 13) {
					double[] features = new double[12];

					for (int i = 0; i < 12; i++) {
						features[i] = Double.parseDouble(values[i]);
					}

					// Assuming the target value is "Abnormal" or "Normal"
					String labelStr = values[labelIndex];
					int label = labelStr.equalsIgnoreCase("Abnormal") ? 1 : 0;

					instances.add(new Instance(features, label));
				} else {
					System.out.println("Skipping line with less than 13 columns: " + String.join("\t", values));
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}

		return new Dataset(instances);
	}

}
