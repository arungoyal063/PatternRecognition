package functions;

/**
 * @author Ondrej Kratochvil
 */
public abstract class Function {

    public abstract double f(double x);

    public double[] f(double[] x) {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; ++i)
            result[i] = f(x[i]);

        return result;
    }

    public abstract String name();
}
