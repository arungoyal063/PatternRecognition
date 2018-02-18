package polynomial_curve_fitting.utils;

import functions.Function;

/**
 * Computes the root-mean-square error for polynomial.
 * @author Ondrej Kratochvil
 */
public class ErrorRMS {

    /**
     *
     * @param function the function for which the RMS error will be computed
     * @param x the double[] representing vector with data
     * @param t the double[] with corresponding targets
     * @return double the root-mean-square error
     */
    public static double get(Function function, double[] x, double[] t) {
        if (x.length != t.length)
            throw new IllegalArgumentException("Length of x and t can't differ");

        int size = x.length;
        double sum = 0;

        for (int n = 0; n < size; ++n)
            sum += Math.pow(function.f(x[n]) - t[n], 2);

        return Math.sqrt(sum/size);
    }
}
