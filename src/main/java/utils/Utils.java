package utils;

import java.util.Arrays;

public class Utils {

    public static double randomDouble(double min, double max) {
        double range = (max - min);
        return (Math.random() * range) + min;
    }

    public static double[] randomArray(int size, double min, double max) {
        double[] x = new double[size];

        for (int i = 0; i < size; ++i)
            x[i] = Utils.randomDouble(min, max);
        return x;
    }

    public static double[] randomSortedArray(int size, double min, double max) {
        double[] x = randomArray(size, min, max);
        Arrays.sort(x);
        return x;
    }
}
