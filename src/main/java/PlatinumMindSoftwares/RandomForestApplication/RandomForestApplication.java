package PlatinumMindSoftwares.RandomForestApplication;
import PlatinumMindSoftwares.RandomForestApplication.classifier.RandomForest;
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

		// Initialize and train the random forest
		int numTrees = 10; // Adjust as needed
		int maxFeatures = 6; // Adjust as needed
		int minSamplesLeaf = 5; // Adjust as needed
		RandomForest randomForest = new RandomForest(dataset, numTrees, maxFeatures, minSamplesLeaf);

		// Get user input for features
		double[] userFeatures = getUserInput();

		// Predict the label using the random forest
		int predictedLabel = randomForest.predictLabel(userFeatures);

		// Display the predicted label
		System.out.println("Predicted Label = " + (predictedLabel == 1 ? "Abnormal" : "Normal"));
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

	// Method to get user input for features
	private static double[] getUserInput() {
		Scanner scanner = new Scanner(System.in);
		double[] userFeatures = new double[12]; // Assuming there are 12 features

		System.out.println("Enter values for each feature:");

		for (int i = 0; i < userFeatures.length; i++) {
			System.out.print("Feature " + (i + 1) + ": ");
			userFeatures[i] = scanner.nextDouble();
		}

		return userFeatures;
	}

}
