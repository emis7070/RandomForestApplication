package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.nodes.LeafNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class LeafNodeTest {
    @Test
    void testLeafNodeConstruction() {
        // Create a LeafNode with three labels
        LeafNode leafNode = new LeafNode(3, List.of(0, 1, 2));

        // Get the label probability distribution
        double[] labelProbDist = leafNode.getLabelProbDist();

        // Assert the expected result
        double[] expectedProbDist = {1.0 / 3, 1.0 / 3, 1.0 / 3};
        assertArrayEquals(expectedProbDist, labelProbDist, 1e-6, "Label probabilities should match");
    }
}
