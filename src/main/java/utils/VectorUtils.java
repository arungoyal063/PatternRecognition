package utils;

import java.util.Arrays;

public class VectorUtils {

    private static double randomDouble(double min, double max) {
        double range = (max - min);
        return (Math.random() * range) + min;
    }

    public static double[] randomVector(int size, double min, double max) {
        double[] x = new double[size];

        for (int i = 0; i < size; ++i)
            x[i] = randomDouble(min, max);
        return x;
    }

    public static double[] randomSortedVector(int size, double min, double max) {
        double[] x = randomVector(size, min, max);
        Arrays.sort(x);
        return x;
    }
}
