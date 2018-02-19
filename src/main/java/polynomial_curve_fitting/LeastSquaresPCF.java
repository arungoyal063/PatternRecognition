package polynomial_curve_fitting;

import Jama.Matrix;
import functions.Function;
import functions.Polynomial;
import polynomial_curve_fitting.utils.Error;

import static java.lang.Math.pow;

/**
 * Basic Polynomial Curve Fitting.
 * Splits available data to training set and validation set.
 * Determines best polynomial coefficients by minimizing an error function with training set.
 *
 * Polynomial:
 * y(x[n], w) = sum(w[j] * x[n]^j) from j=0 to M,
 * where vector w = (w[0], w[1], ... w[M])
 *
 * Least squares method minimizes sum-of-squares error function E(w).
 * E(w) = 1/2 * sum( ((y(x[n], w) - t[n])^2 ) from n=1 to N,
 * where vector x is training and vector t are corresponding targets.
 *
 * Model selection, i.e. selection of the polynomial degree,
 * is done by finding lowest root-mean-square error with validation data for each degree.
 *
 * No regularization used to control the over-fitting.
 *
 * @see polynomial_curve_fitting.PolynomialCurveFitting for vector definitions.
 * @author Ondrej Kratochvil
 */
public class LeastSquaresPCF implements PolynomialCurveFitting {

    private static System.Logger LOGGER = System.getLogger(LeastSquaresPCF.class.getName());

    static final int MAX_DEGREE = 50; // max polynomial degree
    private static final float SPLIT_RATIO = 0.8f; // ratio of training and validation sets

    private int size;
    private double[] x; // vector
    private double[] t; // targets
    double[] w; // coefficients of polynomial that minimizes error

    private int trainingSize; // size of trainingX and trainingT
    private double[] trainingX; // fraction of x, without validation set
    private double[] trainingT; // fraction of t, without validation set

    double[] validationX;
    double[] validationT;

    private double[] degreeToRMS; // maps polynomial degree to RMS error

    /**
     * Basic constructor for Polynomial Curve Fitting consisting of vectors x and t.
     * @param x the vector x
     * @param t the vector t, corresponding targets.
     */
    public LeastSquaresPCF(double[] x, double[] t) {
        this.x = x;
        this.t = t;
        this.size = x.length;
        separateTrainingAndValidationSets();
    }

    /**
     * Splits vector x and t specified with const SPLIT_RATIO to training set and validation set.
     * First half, i.e. training set, is copied to trainingX (trainingT) for vector x (t).
     * Second half, i.e. validation set, is copied to validationX (validationT) for vector x (t).
     */
    private void separateTrainingAndValidationSets() {
        trainingSize = Math.round(x.length * SPLIT_RATIO);

        // prepare training set
        trainingX = new double[trainingSize];
        trainingT = new double[trainingSize];
        System.arraycopy(x, 0, trainingX, 0, trainingSize);
        System.arraycopy(t, 0, trainingT, 0, trainingSize);

        // prepare validation set
        validationX = new double[size - trainingSize];
        validationT = new double[size - trainingSize];
        System.arraycopy(x, 0, validationX, 0, size - trainingSize);
        System.arraycopy(t, 0, validationT, 0, size - trainingSize);
    }

    /**
     * Find optimal polynomial coefficients,
     * where polynomial(x) = sum(w[i] * x^i) from i=0 to M
     */
    void setCoefficients() {
        degreeToRMS = new double[MAX_DEGREE + 1];
        double minimalRMS = Double.MAX_VALUE;
        double RMS;
        double[] tempW;
        Matrix lhs, rhs, ans;

        // model comparison - iterate over all possible polynomial degrees
        for (int degree = 0; degree <= MAX_DEGREE; ++degree) {

            // solve equation
            // sum(A[i][j]*w[j]) from j=0 to M = T[i]
            lhs = getA(degree);
            rhs = getT(degree);

            try {
                ans = lhs.solve(rhs);
            } catch (RuntimeException e) {
                if (e.getMessage().equals("Matrix is singular.")) {
                    LOGGER.log(System.Logger.Level.ERROR, e.getMessage());
                    continue;
                } else {
                    LOGGER.log(System.Logger.Level.ERROR, "Unknown exception: " + e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }

            // extract temporary vector w
            tempW = new double[degree + 1];
            for (int m = 0; m <= degree; ++m)
                tempW[m] = ans.get(m, 0);

            // compute RMS error, use validation data to compute the root-mean-square error
            RMS = Error.rootMeanSquare(new Polynomial(tempW), validationX, validationT);
            degreeToRMS[degree] = RMS;

            // updates vector w if RMS is lower than the previous one found
            if (RMS <= minimalRMS) {
                minimalRMS = RMS;
                w = tempW;
            }
        }
    }

    /**
     * Creates matrix A, where A[i][j] = sum(x[n])^(i+j) from n=1 to N
     * vector x=(x[1], x[2], ... x[N])
     * @param degree the int polynomial degree
     * @return double[][] the matrix A
     */
    Matrix getA(int degree) {
        double[][] A = new double[degree + 1][degree + 1];

        for (int i = 0; i <= degree; ++i) {
            for (int j = 0; j <= degree; ++j) {
                double sum = 0;
                int power = i + j;

                for (int n = 0; n < trainingSize; ++n)
                    sum += pow(trainingX[n], power);

                A[i][j] = sum;
            }
        }
        return new Matrix(A);
    }

    /**
     * Creates vector T, where T[i] = sum(x[n]^i * t[n]) from n=1 to N,
     * vectors x=(x[1], x[2], ... x[N]), t=(t[1], t[2], ... t[N])
     * @param degree the int polynomial degree
     * @return double[] the vector T
     */
    Matrix getT(int degree) {
        double[] T = new double[degree + 1];

        for (int i = 0; i <= degree; ++i) {
            double sum = 0;

            for (int n = 0; n < trainingSize; ++n)
                sum += pow(trainingX[n], i) * trainingT[n];

            T[i] = sum;
        }
        return new Matrix(T, degree + 1);
    }

    @Override
    public Function polynomial() {
        if (w == null) setCoefficients();
        return new Polynomial(w);
    }

    @Override
    public double[] coefficients() {
        if (w == null) setCoefficients();
        return w;
    }

    @Override
    public int degree() {
        return ((Polynomial) polynomial()).degree();
    }

    @Override
    public double[] x() {
        return x;
    }

    @Override
    public double[] t() {
        return t;
    }

    @Override
    public double errorRMS() {
        return Error.rootMeanSquare(polynomial(), x, t);
    }

    @Override
    public double[] getDegreeToRMS() {
        return degreeToRMS;
    }

    @Override
    public double[][] getDegreeLambdaToRMS() {
        return null;
    }
}
