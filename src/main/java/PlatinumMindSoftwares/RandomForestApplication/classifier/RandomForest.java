package PlatinumMindSoftwares.RandomForestApplication.classifier;

import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import PlatinumMindSoftwares.RandomForestApplication.utils.SamplerUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomForest {
    private int numTrees;
    private List<DecisionTree> decisionTrees;

    // size of sampling for each bootstrap step
    private int maxFeatures;

    private int minSamplesLeaf;

    // minimum number of samples for each node. If reached the minimum, we just make it as
    // leaf node without further splitting.
    public static final int TREE_MIN_SIZE = 1;

    private Dataset Dataset;

    public RandomForest(Dataset Dataset, int numTrees, int maxFeatures, int minSamplesLeaf) {
        this.Dataset = Dataset;
        this.numTrees = numTrees;
        this.maxFeatures = maxFeatures;
        this.minSamplesLeaf = minSamplesLeaf;
        decisionTrees = new ArrayList<>(numTrees);
        createRandomForest();
    }

    public void createRandomForest() {
        for (int i = 0; i < numTrees; i++) {
            DecisionTree dt = new DecisionTree();
            dt.buildTree(getBootStrapData(), maxFeatures, minSamplesLeaf);
            decisionTrees.add(dt);
        }
    }


    /**
     * Get the prediction for the input feature vector.  Basically, it iterate
     * through each decision tree, and get prediction from each of them. Then aggregate
     * those predictions.
     *
     * @param featureVector the query input feature vector.
     * @return prediction, which is probability distribution for different
     * labels.
     */
    public double[] predictDist(double[] featureVector, int totalNumLabels) {
        double[] finalPredict = new double[totalNumLabels];
        // iterate through each decision tree, and make prediction.
        for (int i = 0; i < numTrees; i++) {
            double[] predict = decisionTrees.get(i).predictDist(featureVector);
            for (int j = 0; j < totalNumLabels; j++) {
                finalPredict[j] += predict[j];
            }
        }
        for (int i = 0; i < totalNumLabels; i++) {
            finalPredict[i] = finalPredict[i] / numTrees;
        }
        return finalPredict;
    }

    public int predictLabel(RandomForest randomForest, double[] featureVector, int totalNumLabels) {
        double[] predictedDistribution = randomForest.predictDist(featureVector, totalNumLabels);

        // Find the label with the highest probability as the predicted label
        int predictedLabel = 0;
        double maxProbability = predictedDistribution[0];
        for (int i = 1; i < totalNumLabels; i++) {
            if (predictedDistribution[i] > maxProbability) {
                maxProbability = predictedDistribution[i];
                predictedLabel = i;
            }
        }

        return predictedLabel;
    }

    private Dataset getBootStrapData() {
        int[] indexs = SamplerUtils.bootStrap(Dataset.getNumOfInstances());
        List<Instance> bootStrapSamples = new ArrayList<Instance>();
        for (int i = 0; i < indexs.length; i++) {
            bootStrapSamples.add(Dataset.getInstance(indexs[i]));
        }
        return new Dataset(bootStrapSamples);
    }
}
