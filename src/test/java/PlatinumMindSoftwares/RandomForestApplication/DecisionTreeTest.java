package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.classifier.DecisionTree;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import org.junit.jupiter.api.Test;

import static PlatinumMindSoftwares.RandomForestApplication.DatasetReaderTest.createTestDataset;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecisionTreeTest {

    // Assume you have a DecisionTree instance and a test dataset
    DecisionTree decisionTree = new DecisionTree();
    Dataset dataset = createTestDataset();
    @Test
    void test_PredictLabel_ForAbnormalInstance() {
        // Create a feature vector representing an instance expected to be classified as "Abnormal"
        double[] testFeatures = {63.0278175, 22.55258597, 39.60911701, 40.47523153, 98.67291675, -0.254399986, 0.744503464, 12.5661, 14.5386, 15.30468, -28.658501, 43.5123};

        // Build the decision tree (you may have already done this in a setup method or a previous test)
        decisionTree.buildTree(dataset, 2, 2);

        // Test the prediction
        int predictedLabel = decisionTree.predictLabel(testFeatures);

        // Assert the expected result
        assertEquals(1, predictedLabel, "Predicted label should be 1 (Abnormal)");
    }

    @Test
    void test_PredictLabel_ForNormalInstance() {
        // Create a feature vector representing an instance expected to be classified as "Normal"
        double[] testFeatures = {33.84164075, 5.073991409, 36.64123294, 28.76764934, 123.9452436, -0.199249089, 0.674504089, 19.3825, 17.6963, 13.72929, 1.783007, 40.6049};

        // Build the decision tree
        decisionTree.buildTree(dataset, 2, 2);

        // Test the prediction
        int predictedLabel = decisionTree.predictLabel(testFeatures);

        // Assert the expected result
        assertEquals(0, predictedLabel, "Predicted label should be 0 (Normal)");
    }

}
