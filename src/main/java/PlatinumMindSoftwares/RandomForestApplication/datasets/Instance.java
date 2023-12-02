package PlatinumMindSoftwares.RandomForestApplication.datasets;

import java.util.Arrays;

/**
 * class appears to represent a single instance or data point in your dataset
 *  each instance typically corresponds to a single data entry
 *
 *  Feature Vector (featureVector):    This is an array of numerical values representing the features or attributes of the instance.
 *          such as pelvic incidence, pelvic tilt, lumbar lordosis angle,
 *
 *  Label Index (labelIndex):  This is an integer representing the class or label of the instance.
 *          the label seems to be either "Abnormal" or "Normal."
 *
 *
 */
public class Instance {
    private final double[] featureVector;
    private final int labelIndex;

    public Instance(double[] featureVector, int labelIndex) {
        this.featureVector = featureVector;
        this.labelIndex = labelIndex;
    }

    public double[] getFeatureVector() {
        return this.featureVector;
    }

    public int getLabelIndex() {
        return labelIndex;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "featureVector=" + Arrays.toString(featureVector) +
                ",labelIndex=" + labelIndex +
                '}';
    }
}
