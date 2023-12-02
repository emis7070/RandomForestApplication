package PlatinumMindSoftwares.RandomForestApplication.datasets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class that is used to split a given dataset into training and testing sets
 *  particularly for training and evaluating the model for predicting Lower Back Pain symptoms.
 */

public class TestTrain {
    public final Dataset test;
    public final Dataset train;

    /**
     *
     * @param input This is an object of type Dataset representing the entire dataset that needs to be split.
     * @param splitSize This is an integer representing the size of the training set. It determines the proportion of the data that should be used for training.
     * @param rng This is an object of type Random used to introduce randomness in the dataset splitting process
     */
    public TestTrain(Dataset input, int splitSize, Random rng) {
        /**
         * creating two list from object Instance they are empty at this moment
         *  st: training
         *  nd: testing
         */
        List<Instance> train = new ArrayList<>();
        List<Instance> test = new ArrayList<>();

        for (int i = 0; i < input.getSize(); i++) {
            Instance sample = input.getInstance(i); // getting each Instance object from the raw data inputted
            // splitting the data
            if (rng.nextDouble() < (splitSize - train.size()) / (double) (input.getSize() - i)) {
                train.add(sample);
            } else {
                test.add(sample);
            }
        }
        this.train = new Dataset(train);
        this.test = new Dataset(test);
    }

}
