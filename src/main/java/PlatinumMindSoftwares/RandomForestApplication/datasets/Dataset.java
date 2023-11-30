package PlatinumMindSoftwares.RandomForestApplication.datasets;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private final List<Instance> trainingData;

    // total number of instance in this training set
    private int numOfInstance;//instance typically corresponds to a single row in your dataset. For example, if your dataset has 100 rows, numOfInstance would be set to 100.
    // total number of features.
    private int numOfFeatures; // if each row in your dataset has 12 attributes (columns), numOfFeatures would be set to 12.

    public Dataset(List<Instance> trainingData) {
        this.trainingData = trainingData;
        init();
    }

    public Dataset(List<double[]> featureVectors, List<Integer> labels) {
        trainingData = new ArrayList<>();
        for (int i = 0; i < featureVectors.size(); i++) {
            trainingData.add(new Instance(featureVectors.get(i), labels.get(i)));
        }
        init();
    }

    private void init() {
        this.numOfInstance = trainingData.size();
        this.numOfFeatures = trainingData.get(0).getFeatureVector().length;
    }

    public int getNumOfInstances() {
        return numOfInstance;
    }

    public int getNumOfFeatures() {
        return numOfFeatures;
    }

    public List<Integer> getLabels() {
        List<Integer> labels = new ArrayList<Integer>();
        for (Instance instance : trainingData) {
            labels.add(instance.getLabelIndex());
        }

        return labels;
    }

    public Instance getInstance(int i) {
        return trainingData.get(i);
    }

    public int getSize() {
        return trainingData.size();
    }

    public List<Instance> getTrainingData() {
        return trainingData;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "trainingData=" + trainingData +
                ", numOfInstance=" + numOfInstance +
                ", numOfFeatures=" + numOfFeatures +
                '}';
    }
}
