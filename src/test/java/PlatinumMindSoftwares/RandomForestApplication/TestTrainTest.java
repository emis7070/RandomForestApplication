package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import PlatinumMindSoftwares.RandomForestApplication.datasets.TestTrain;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static PlatinumMindSoftwares.RandomForestApplication.RandomForestApplication.readDatasetFromCSV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrainTest {
    @Test
    public void testDatasetSplitting() {
        /**
         * GET DATASET
         */
        String filePath = "src/main/java/PlatinumMindSoftwares/RandomForestApplication/datasets/Dataset_spine.csv";
        int labelIndex = 12; // Assuming the target value is in the last column
        Dataset inputDataset = readDatasetFromCSV(filePath,labelIndex);

        // Set the seed for reproducibility
        Random rng = new Random(42);

        // Create a TestTrain instance with a split size of 70%
        TestTrain testTrain = new TestTrain(inputDataset, 70, rng);

        // Check if the sizes of the training and testing sets add up to the total inputDataset size
        assertEquals(inputDataset.getSize(), testTrain.train.getSize() + testTrain.test.getSize());

        System.out.println("Actual inputDataset Size: " + inputDataset.getSize());
        System.out.println("Actual Train Size: " + testTrain.train.getSize());
        System.out.println("Actual Test Size: " + testTrain.test.getSize());
    }
}
