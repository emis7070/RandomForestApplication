package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.classifier.RandomForest;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static PlatinumMindSoftwares.RandomForestApplication.DatasetReaderTest.createTestDataset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomForestTest {
    /**
     * RandomForest = new RandomForest(dataset, 3, 2, 2);
     * dataset: It contains instances of data points with their features and corresponding labels.
     * 3: This is the number of decision trees in the random forest. In this case, the random forest is configured to have 3 decision trees.
     * 2, meaning that, at each split, only 2 features are considered for making a decision
     *
     *
     * Suppose you have a dataset with features like age, income, and education level, and you want to build a random forest to predict whether a person will buy a product or not based on these features. You have 100 instances in your dataset.
     *
     * dataset: Your dataset with 100 instances.
     * 3: You decide to create a random forest with 3 decision trees.
     * 2: At each split in a decision tree, only 2 features will be considered (e.g., age and
     *
     *
     */
    @Test
    void testRandomForestPrediction() {
        // Create a simple dataset for testing
        Dataset dataset = createTestDataset();

        // Instantiate a RandomForest object
        RandomForest randomForest = new RandomForest(dataset, 3, 2, 2);

        // Test prediction for a known instance
        double[] testFeatures = {63.0278175, 22.55258597, 39.60911701, 40.47523153, 98.67291675, -0.254399986, 0.744503464, 12.5661, 14.5386, 15.30468, -28.658501, 43.5123};
        double[] predictedDistribution = randomForest.predictDist(testFeatures,2);
        // Print the predicted distribution
        System.out.println("Predicted Distribution:");
        for (double probability : predictedDistribution) {
            System.out.print(probability + "\t");
        }

        // Assert that the predicted distribution has the expected size
        assertEquals(2, predictedDistribution.length, "Predicted distribution should have 2 elements");

        // Assert that the sum of probabilities in the distribution is approximately 1
        double sumProbabilities = 0.0;
        for (double probability : predictedDistribution) {
            sumProbabilities += probability;
        }
        assertEquals(1.0, sumProbabilities, 0.0001, "Sum of probabilities should be approximately 1.0");
    }
}
