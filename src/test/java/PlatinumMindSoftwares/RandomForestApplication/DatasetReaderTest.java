package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import org.junit.jupiter.api.Test;

import static PlatinumMindSoftwares.RandomForestApplication.RandomForestApplication.readDatasetFromCSV;
import static org.junit.jupiter.api.Assertions.*;

public class DatasetReaderTest {
    @Test
    void testReadDatasetFromCSV() {
        // Provide a sample CSV file path for testing
        String filePath = "src/main/java/PlatinumMindSoftwares/RandomForestApplication/datasets/Dataset_spine.csv";
        int labelIndex = 12; // Assuming the target value is in the last column

        // Call the readDatasetFromCSV function
        Dataset dataset = RandomForestApplication.readDatasetFromCSV(filePath, labelIndex);

        // Check if the dataset is not null
        assertNotNull(dataset, "Dataset should not be null");

        // Check if the dataset contains instances
        assertTrue(dataset.getSize() > 0, "Dataset should contain instances");

        // Check the label values for the first instance
        Instance firstInstance = dataset.getInstance(0);
        assertEquals(1, firstInstance.getLabelIndex(), "First instance label should be 1 (Abnormal)");


        // Print information for verification (optional)
        System.out.println("Dataset Size: " + dataset.getSize());
        System.out.println("First Instance: " + firstInstance);
    }

    public static Dataset createTestDataset() {
        String filePath = "src/main/java/PlatinumMindSoftwares/RandomForestApplication/datasets/Dataset_spine.csv";
        int labelIndex = 12; // Assuming the target value is in the last column
        return readDatasetFromCSV(filePath,labelIndex);
    }
}
