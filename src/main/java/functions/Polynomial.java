package functions;

/**
 * @author Ondrej Kratochvil
 */
public class Polynomial extends Function {

    private double[] coefficients;
    private int degree;

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
        degree = coefficients.length - 1;
    }

    public double f(double x) {
        double result = coefficients[0];

        for (int j = 1; j <= degree; ++j)
            result += coefficients[j] * Math.pow(x, j);

        return result;
    }

    @Override
    public String name() {
        return "polynomial";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= degree; ++i) {
            sb.append(coefficients[i]);
            sb.append("*x^");
            sb.append(i);
            if (i != degree) sb.append(' ');
        }
        return "polynomial(x) = " + sb;
    }
}
