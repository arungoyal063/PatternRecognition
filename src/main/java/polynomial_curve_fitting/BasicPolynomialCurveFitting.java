package polynomial_curve_fitting;

import Jama.Matrix;
import functions.Polynomial;
import polynomial_curve_fitting.utils.ErrorRMS;


/**
 * Basic Polynomial Curve Fitting.
 * Splits available data to training set and validation set.
 * Determines best polynomial coefficients by minimizing an error function with training set.
 * Optimizes with validation set.
 * No regularization used to control the over-fitting.
 *
 * @author Ondrej Kratochvil
 */
public class BasicPolynomialCurveFitting extends PolynomialCurveFitting {

    private static final int MAX_DEGREE = 50; // max polynomial degree
    private static final float SPLIT_RATIO = 0.9f;

    private double[] trainingX; // fraction of x, without validation set
    private double[] trainingT; // fraction of t, without validation set
    private int trainingSize; // size of trainingX and trainingT

    private double[] degreeToRMS; // maps polynomial degree to RMS error

    /**
     * Basic constructor for Polynomial Curve Fitting consisting of vectors x and t.
     * @param x the vector x
     * @param t the vector t, corresponding targets.
     */
    public BasicPolynomialCurveFitting(double[] x, double[] t) {
        super(x, t);
        separateTrainingAndValidationSets();
        w = getCoefficients();
    }

    /**
     * Splits vector x and t specified with const SPLIT_RATIO to training set and validation set.
     * First half, i.e. training set, is copied to trainingX/trainingT for vector x/t.
     * Vectors x and t remains untouched, i.e. training set and validation set together.
     */
    private void separateTrainingAndValidationSets() {
        trainingSize = Math.round(x.length * SPLIT_RATIO);

        // prepare training set
        trainingX = new double[trainingSize];
        trainingT = new double[trainingSize];
        System.arraycopy(x, 0, trainingX, 0, trainingSize);
        System.arraycopy(t, 0, trainingT, 0, trainingSize);
    }

    /**
     * Find optimal polynomial coefficients,
     * where polynomial(x) = sum(w[i] * x^i) from i=0 to M
     * @return double[] the vector w,
     */
    protected double[] getCoefficients() {
        degreeToRMS = new double[MAX_DEGREE + 1];
        double minimalRMS = Double.MAX_VALUE;
        double RMS;
        double[] tempW;
        Matrix lhs, rhs, ans;

        // iterate over all possible polynomial degrees
        for (int degree = 0; degree <= MAX_DEGREE; ++degree) {

            // solve equation
            // sum(A[i][j]*w[j]) from j=0 to M = T[i]
            lhs = new Matrix(getA(degree));
            rhs = new Matrix(getT(degree), degree + 1);
            ans = lhs.solve(rhs);

            // extract temporary vector w
            tempW = new double[degree + 1];
            for (int m = 0; m <= degree; ++m)
                tempW[m] = ans.get(m, 0);

            // compute RMS error, use all available data (training + validation) to compute the root-mean-square error
            RMS = ErrorRMS.get(new Polynomial(tempW), x, t);
            degreeToRMS[degree] = RMS;

            // updates vector w if RMS is lower than the previous one found
            if (RMS <= minimalRMS) {
                minimalRMS = RMS;
                w = tempW;
            }
        }
        return w;
    }

    /**
     * Creates vector T, where T[i] = sum(x[n]^i * t[n]) from n=1 to N,
     * vectors x=(x[1], x[2], ... x[N]), t=(t[1], t[2], ... t[N])
     * @param degree the int polynomial degree
     * @return double[] the vector T
     */
    private double[] getT(int degree) {
        double[] T = new double[degree + 1];

        for (int i = 0; i <= degree; ++i) {
            double sum = 0;

            for (int n = 0; n < trainingSize; ++n)
                sum += Math.pow(trainingX[n], i) * trainingT[n];

            T[i] = sum;
        }
        return T;
    }

    /**
     * Creates matrix A, where A[i][j] = sum(x[n])^(i+j) from n=1 to N
     * vector x=(x[1], x[2], ... x[N])
     * @param degree the int polynomial degree
     * @return double[][] the matrix A
     */
    private double[][] getA(int degree) {
        double[][] A = new double[degree + 1][degree + 1];

        for (int i = 0; i <= degree; ++i) {
            for (int j = 0; j <= degree; ++j) {
                double sum = 0;
                int power = i + j;

                for (int n = 0; n < trainingSize; ++n)
                    sum += Math.pow(trainingX[n], power);

                A[i][j] = sum;
            }
        }
        return A;
    }

    /**
     * Getter for degreeToRMS
     * @return double[] the mapped polynomial degree to its RMS error
     */
    public double[] getDegreeToRMS() {
        return degreeToRMS;
    }
}
