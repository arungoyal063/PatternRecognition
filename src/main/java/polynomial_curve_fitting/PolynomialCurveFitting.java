package polynomial_curve_fitting;

import functions.Function;
import functions.Polynomial;
import polynomial_curve_fitting.utils.ErrorRMS;

/**
 * Abstract class Polynomial Curve Fitting
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
public abstract class PolynomialCurveFitting {

    double[] x; // vector
    double[] t; // targets
    int size;

    double[] w; // coefficients of polynomial that minimizes error
    int degree;

    /**
     * Basic constructor for Polynomial Curve Fitting consisting of vectors x and t.
     * @param x the vector x
     * @param t the vector t, corresponding targets.
     */
    public PolynomialCurveFitting(double[] x, double[] t) {
        this.x = x;
        this.t = t;
        this.size = x.length;
    }

    /**
     * Find optimal polynomial coefficients,
     * where polynomial(x) = sum(w[i] * x^i) from i=0 to M
     * @return double[] the vector w,
     */
    protected abstract double[] getCoefficients();

    public double[] getVector() { return x; }
    public double[] getTargets() { return t; }

    /**
     * Computes the root-mean-square error for polynomial that minimizes error.
     * @return double the root-mean-square error
     */
    public double errorRMS() {
        return ErrorRMS.get(getPolynomial(), x, t);
    }

    /**
     * Creates new instance of Polynomial that minimizes error.
     * @return the Polynomial
     */
    public Function getPolynomial() {
        if (w == null)
            w = getCoefficients();

        return new Polynomial(w);
    }

    /**
     * Getter for degree of optimal polynomial
     * @return int the degree
     */
    public int getDegree() {
        return degree;
    }
}
