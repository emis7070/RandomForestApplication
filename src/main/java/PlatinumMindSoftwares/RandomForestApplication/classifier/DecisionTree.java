package PlatinumMindSoftwares.RandomForestApplication.classifier;

import PlatinumMindSoftwares.RandomForestApplication.datasets.Dataset;
import PlatinumMindSoftwares.RandomForestApplication.datasets.Instance;
import PlatinumMindSoftwares.RandomForestApplication.nodes.DecisionNode;
import PlatinumMindSoftwares.RandomForestApplication.nodes.LeafNode;
import PlatinumMindSoftwares.RandomForestApplication.nodes.TreeNode;
import PlatinumMindSoftwares.RandomForestApplication.utils.EntropyUtils;
import PlatinumMindSoftwares.RandomForestApplication.utils.SamplerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DecisionTree {
    // root of the decision tree.
    private TreeNode root;

    // for each node, we randomly select subset of features to consider for splitting
    // by default, we set the size as square root of total number of features.
    private int maxFeatures;

    private int numLabels = 3;

    // minimum size of subtree, this value can be used as condition for termination.
    // by default, we set the size as 10.
    private int minSamplesLeaf = 10;

    DecisionTree() {
    }

    void buildTree(Dataset Dataset, int maxFeatures, int minSamplesLeaf) {
        this.maxFeatures = maxFeatures;
        this.minSamplesLeaf = minSamplesLeaf;
        root = build(Dataset);
    }

    double[] predictDist(double[] featureVector) {
        return predict(root, featureVector);
    }

    public int predictLabel(double[] featureVector) {
        double[] dist = predict(root, featureVector);
        int maxLabel = 0;
        double maxProb = 0;
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] > maxProb) {
                maxProb = dist[i];
                maxLabel = i;
            }
        }

        return maxLabel;
    }

    private double[] predict(TreeNode node, double[] featureVector) {
        // if leaf node, then just return the distribution.
        if (node instanceof LeafNode)
            return ((LeafNode) node).getLabelProbDist();

        // if current node is decision node, then go to left child or right child.
        int featureIdx = ((DecisionNode) node).getSplittingFeatureIndex();
        double splittingValue = ((DecisionNode) node).getSplittingValue();
        double value = featureVector[featureIdx];
        if (value < splittingValue)
            return predict(((DecisionNode) node).getLeftChild(), featureVector);
        else
            return predict(((DecisionNode) node).getRightChild(), featureVector);
    }

    private TreeNode build(Dataset Dataset) {
        // create a new leaf node if either of condition met.
        if (Dataset.getNumOfInstances() < minSamplesLeaf || hasSameLabel(Dataset.getLabels())) {
            return new LeafNode(numLabels, Dataset.getLabels());
        }

        // sub-sample the attributes.
        int[] selectedFeatureIndexes = SamplerUtils.randSample(Dataset.getNumOfFeatures(), maxFeatures);

        // select the best feature based on information gain
        int bestFeatureIndex = getBestFeatureIndex(selectedFeatureIndexes, Dataset);

        // for numerical attribute, we  create left and right child.
        return createDecisionNode(bestFeatureIndex, Dataset);
    }


    private int getBestFeatureIndex(int[] candidateFeatureIndex, Dataset Dataset) {
        double maxInfoGain = Double.MIN_VALUE;
        int bestFeatureIndex = 0;
        double entropy = EntropyUtils.getEntropy(Dataset.getLabels());

        for (int i = 0; i < candidateFeatureIndex.length; i++) {
            int featureIndex = candidateFeatureIndex[i];
            double infoGain = getInformationGain(entropy, featureIndex, Dataset);
            if (infoGain > maxInfoGain) {
                maxInfoGain = infoGain;
                bestFeatureIndex = i;
            }
        }
        return bestFeatureIndex;
    }


    private double getInformationGain(double entropy, int featureIndex, Dataset Dataset) {
        int dataSize = Dataset.getNumOfInstances();
        // get the mean
        double mean = 0;
        for (int i = 0; i < Dataset.getNumOfInstances(); i++) {
            double[] featureVector = Dataset.getInstance(i).getFeatureVector();
            mean += featureVector[featureIndex] / dataSize;
        }

        // divide the Dataset into two subset, based on the mean value.
        int leftSize = 0;
        for (int i = 0; i < dataSize; i++) {
            if ((Dataset.getInstance(i).getFeatureVector())[featureIndex] < mean)
                leftSize++;
        }
        int rightSize = dataSize - leftSize;

        List<Integer> leftLabels = new ArrayList<>(leftSize);
        List<Integer> rightLabels = new ArrayList<>(rightSize);

        for (int i = 0; i < dataSize; i++) {
            if (Dataset.getInstance(i).getFeatureVector()[featureIndex] < mean)
                leftLabels.add(Dataset.getInstance(i).getLabelIndex());
            else
                rightLabels.add(Dataset.getInstance(i).getLabelIndex());
        }

        double leftEntropy = EntropyUtils.getEntropy(leftLabels);
        double rightEntropy = EntropyUtils.getEntropy(rightLabels);

        return entropy - (leftSize * 1.0 / dataSize) * leftEntropy
                - (rightSize * 1.0 / dataSize) * rightEntropy;
    }

    private TreeNode createDecisionNode(int bestFeatureIdx, Dataset Dataset) {
        // calculate the mean.
        double mean = 0;
        int dataSize = Dataset.getNumOfInstances();
        for (int i = 0; i < dataSize; i++) {
            double[] featureVector = Dataset.getInstance(i).getFeatureVector();
            mean += featureVector[bestFeatureIdx] / dataSize;
        }

        List<Instance> leftDataset = new ArrayList<>();
        List<Instance> rightDataset = new ArrayList<>();
        // divide the Datasets into two subset, based on the mean value.
        for (int i = 0; i < dataSize; i++) {
            // smaller one goes to left.
            if ((Dataset.getInstance(i).getFeatureVector())[bestFeatureIdx] < mean)
                leftDataset.add(Dataset.getInstance(i));
            else
                rightDataset.add(Dataset.getInstance(i));
        }

        // create new decision node, and set the left child and right child.
        TreeNode node = new DecisionNode(bestFeatureIdx, mean);
        if (leftDataset.size() > 0) {
            ((DecisionNode) node).setLeftChild(build(new Dataset(leftDataset)));
        } else {
            // create leaf node, with majority distribution.
            TreeNode leafNode = new LeafNode(numLabels, Dataset.getLabels());
            ((DecisionNode) node).setLeftChild(leafNode);
        }

        if (rightDataset.size() > 0) {
            ((DecisionNode) node).setRightChild(build(new Dataset(rightDataset)));
        } else {
            // create leaf node, with majority distribution.
            TreeNode leafNode = new LeafNode(numLabels, Dataset.getLabels());
            ((DecisionNode) node).setRightChild(leafNode);
        }

        return node;
    }

    private boolean hasSameLabel(List<Integer> labels) {
        for (int i = 1; i < labels.size(); i++) {
            if (!Objects.equals(labels.get(i), labels.get(i - 1)))
                return false;
        }
        return true;
    }

    public void setMaxFeatures(int maxFeatures) {
        this.maxFeatures = maxFeatures;
    }

    public void setTreeMinSize(int minTreeSize) {
        this.minSamplesLeaf = minTreeSize;
    }
}
