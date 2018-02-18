package functions;

/**
 * Function of one variable.
 * @author Ondrej Kratochvil
 */
public abstract class Function {

    /**
     * Computes function value for f(x).
     * @param x the argument
     * @return double the function value
     */
    public abstract double f(double x);

    /**
     * Computes function values for whole array of arguments.
     * @param x the double[] for arguments
     * @return double[] the function values
     */
    public double[] f(double[] x) {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; ++i)
            result[i] = f(x[i]);

        return result;
    }

    /**
     * Getter for function name.
     */
    public abstract String name();
}
