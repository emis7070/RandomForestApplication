package PlatinumMindSoftwares.RandomForestApplication;
import PlatinumMindSoftwares.RandomForestApplication.classifier.RandomForest;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import PlatinumMindSoftwares.RandomForestApplication.datasets.TestTrain;
import PlatinumMindSoftwares.RandomForestApplication.utils.EntropyUtils;
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

		// Specify the random seed for reproducibility
		long seed = 123;
		Random rng = new Random(seed);

		// Split the dataset into training and test sets using TestTrain class
		int splitSize = 80; // 80% for training
		TestTrain testTrainSplit = new TestTrain(dataset, splitSize, rng);

		// Initialize and train the random forest on the training set
		int numTrees = 10; // Adjust as needed
		int maxFeatures = 6; // Adjust as needed
		int minSamplesLeaf = 5; // Adjust as needed
		RandomForest randomForest = new RandomForest(testTrainSplit.train, numTrees, maxFeatures, minSamplesLeaf);

		// Evaluate the model on the test set
		int correctPredictions = 0;
		for (int i = 0; i < testTrainSplit.test.getSize(); i++) {
			Instance instance = testTrainSplit.test.getInstance(i);
			double[] features = instance.getFeatureVector();
			int actualLabel = instance.getLabelIndex();

			// Predict the label using the random forest
			int predictedLabel = randomForest.predictLabel(features);

			// Compare predicted label with actual label
			if (predictedLabel == actualLabel) {
				correctPredictions++;
			}
		}

		// Calculate accuracy on the test set
		double accuracy = (double) correctPredictions / testTrainSplit.test.getSize();
		System.out.println("Test Accuracy: " + (accuracy * 100) + "%");

		/**
		 * calculated the entropy of your training and test sets.
		 * The entropy values provide insights into the impurity or disorder within the datasets.
		 * Lower entropy values indicate that the dataset is more pure, which is often desirable.
		 */

		// Calculate entropy of the labels in the training set
		List<Integer> trainLabels = testTrainSplit.train.getLabels();
		double trainSetEntropy = EntropyUtils.getEntropy(trainLabels);
		System.out.println("Entropy of the training set: " + trainSetEntropy);

		// Calculate entropy of the labels in the test set
		List<Integer> testLabels = testTrainSplit.test.getLabels();
		double testSetEntropy = EntropyUtils.getEntropy(testLabels);
		System.out.println("Entropy of the test set: " + testSetEntropy);

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
