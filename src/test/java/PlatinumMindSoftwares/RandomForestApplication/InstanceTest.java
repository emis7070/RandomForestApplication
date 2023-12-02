package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstanceTest {
    @Test
    void testInstanceCreation() {
        // Create a sample feature vector and label index
        double[] featureVector = {63.0278175, 22.55258597, 39.60911701, 40.47523153, 98.67291675, -0.254399986, 0.744503464, 12.5661, 14.5386, 15.30468, -28.658501, 43.5123};
        int labelIndex = 12;

        // Create an Instance object
        Instance instance = new Instance(featureVector, labelIndex);

        // Check if the feature vector and label index are set correctly
        assertArrayEquals(featureVector, instance.getFeatureVector());
        assertEquals(labelIndex, instance.getLabelIndex());
    }
    @Test
    void testInstanceToString() {
        double[] featureVector = {63.0278175, 22.55258597, 39.60911701, 40.47523153, 98.67291675, -0.254399986, 0.744503464, 12.5661, 14.5386, 15.30468, -28.658501, 43.5123};
        int labelIndex = 12;

        Instance instance = new Instance(featureVector, labelIndex);

        String expectedString = "Instance{" +
                "featureVector=" + Arrays.toString(featureVector) +
                ",labelIndex=" + labelIndex +
                '}';

        assertEquals(expectedString, instance.toString());
    }
}
