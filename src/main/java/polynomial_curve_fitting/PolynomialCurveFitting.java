package polynomial_curve_fitting;

import functions.Function;

/**
 * Polynomial Curve Fitting
 *
 * N is the size of the set
 * M is the polynomial degree
 *
 * x = (x[1], x[2], ... x[N]) and t = (t[1], t[2], ... t[N]),
 * where vector x is training set with corresponding targets, i.e. vector t
 *
 * w = (w[0], w[1], ... w[M]),
 * where vector w corresponds to polynomial coefficients, polynomial(x) = sum(w[i] * x^i) from i=0 to M
 *
 * @author Ondrej Kratochvil
 */
public interface PolynomialCurveFitting {

    /**
     * Getter for optimal polynomial
     * @return Function the polynomial
     */
    Function polynomial();

    /**
     * Find optimal polynomial coefficients,
     * where polynomial(x) = sum(w[i] * x^i) from i=0 to M
     * @return double[] the vector w
     */
    double[] coefficients();

    /**
     * Getter for degree of optimal polynomial
     * @return int the degree
     */
    int degree();

    /**
     * Getter for vector x
     * @return double[] the vector x
     */
    double[] x();

    /**
     * Getter for vector t
     * @return double[] the vector t
     */
    double[] t();

    /**
     * Computes the root-mean-square error for optimal polynomial
     * @return double the root-mean-square error
     */
    double errorRMS();

    /**
     * Getter for degreeToRMS
     * @return double[] the mapped polynomial degree to its RMS error
     */
    double[] getDegreeToRMS();
}
