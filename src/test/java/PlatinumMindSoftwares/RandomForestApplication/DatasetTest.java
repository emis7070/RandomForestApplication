package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatasetTest {
    @Test
    public void testNumOfInstances() {
        // Arrange
        List<Instance> instances = createDummyInstances(5);
        Dataset dataset = new Dataset(instances);

        // Act
        int numOfInstances = dataset.getNumOfInstances();

        // Assert
        assertEquals(5, numOfInstances);
    }

    @Test
    public void testNumOfFeatures() {
        // Arrange
        List<Instance> instances = createDummyInstances(5);
        Dataset dataset = new Dataset(instances);

        // Act
        int numOfFeatures = dataset.getNumOfFeatures();

        // Assert
        assertEquals(10, numOfFeatures); // Assuming each instance has 10 features
    }

    @Test
    public void testGetLabels() {
        // Arrange
        List<Instance> instances = createDummyInstances(3);
        Dataset dataset = new Dataset(instances);

        // Act
        List<Integer> labels = dataset.getLabels();

        // Assert
        List<Integer> expectedLabels = Arrays.asList(1, 2, 3); // Assuming labels are 1, 2, 3
        assertEquals(expectedLabels, labels);
    }

    private List<Instance> createDummyInstances(int count) {
        List<Instance> instances = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double[] featureVector = new double[10]; // Assuming 10 features
            int labelIndex = i + 1; // Label index starts from 1
            instances.add(new Instance(featureVector, labelIndex));
        }
        return instances;
    }
}
