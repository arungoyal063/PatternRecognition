package polynomial_curve_fitting.utils;

import functions.Function;

import static java.lang.Math.*;

/**
 * Computes the root-mean-square error for polynomial.
 * @author Ondrej Kratochvil
 */
public class Error {

    /**
     *
     * @param function the function for which the RMS error will be computed
     * @param x the double[] representing vector with data
     * @param t the double[] with corresponding targets
     * @return double the root-mean-square error
     */
    public static double rootMeanSquare(Function function, double[] x, double[] t) {
        double sum = sumOfSquares(function, x, t);
        int n = x.length;
        return sqrt(2 * sum / n);
    }


    /**
     * Computes sum-of-squares error.
     * @param function the function for which the RMS error will be computed
     * @param x the double[] representing vector with data
     * @param t the double[] with corresponding targets
     * @return double the root-mean-square error
     */
    public static double sumOfSquares(Function function, double[] x, double[] t) {
        double sum = 0;
        for (int n = 0; n < x.length; ++n)
            sum += pow(function.f(x[n]) - t[n], 2);

        return sum;
    }
}


