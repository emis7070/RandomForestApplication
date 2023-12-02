package PlatinumMindSoftwares.RandomForestApplication;

import PlatinumMindSoftwares.RandomForestApplication.utils.SamplerUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SamplerUtilsTest {

    @Test
    void testRandSample() {
        int n = 10; // Population size
        int m = 5;  // Sample size

        int[] sampleIndexes = SamplerUtils.randSample(n, m);
        System.out.println("Sampled indexes: " + Arrays.toString(sampleIndexes));

        // Check if the length of the sample is correct
        assertEquals(m, sampleIndexes.length, "Sample size should be " + m);

        // Check if all elements are unique
        Set<Integer> indexSet = new HashSet<>();
        for (int index : sampleIndexes) {
            assertTrue(index >= 0 && index < n, "Index should be in the range [0, n)");
            assertTrue(indexSet.add(index), "Duplicate index found");
        }
    }

    @Test
    void testBootStrap() {
        int n = 10; // Population size

        int[] bootstrapIndexes = SamplerUtils.bootStrap(n);

        // Check if the length of the bootstrap sample is correct
        assertEquals(n, bootstrapIndexes.length, "Bootstrap sample size should be " + n);

        // Check if all elements are within the range [0, n)
        for (int index : bootstrapIndexes) {
            assertTrue(index >= 0 && index < n, "Index should be in the range [0, n)");
        }

        // Check if the bootstrap sample contains unique elements
        Set<Integer> indexSet = new HashSet<>();
        Arrays.stream(bootstrapIndexes).forEach(index -> assertTrue(indexSet.add(index), "Duplicate index found"));
    }
}
