package PlatinumMindSoftwares.RandomForestApplication.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * entropy is commonly used to measure impurity.
 *
 *  H(X)=0 when all samples in the set belong to the same class (perfectly pure set).
 *      A lower entropy value indicates that the set is more homogeneous, meaning there is less disorder or uncertainty about the class labels.
 *
 *  H(X)=1 when the samples are evenly distributed across all classes (maximum impurity).
 *      A higher entropy value indicates more disorder or mixed class labels in the set.
 */
public class EntropyUtils {
    /*
     * calculate the entropy of response variables.
     * H(X) = -\sum_{i=1}^{d} p(x_{i})log2(p(x_{i}))
     *
     */
    public static double getEntropy(List<Integer> labels) {
        int size = labels.size();
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int i = 0; i < labels.size(); i++) {
            if (!countMap.containsKey(labels.get(i))) {
                countMap.put(labels.get(i), 1);
            } else {
                int currCnt = countMap.get(labels.get(i));
                countMap.put(labels.get(i), currCnt + 1);
            }
        }

        double entropy = 0;
        for (Integer label : countMap.keySet()) {
            double p = countMap.get(label) * 1.0 / size;
            entropy += p * log(p, 2);
        }

        return -1 * entropy;
    }

    private static double log(double x, int base) {
        return Math.log(x) / Math.log(base);
    }
}
