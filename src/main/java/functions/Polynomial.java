package functions;

import static java.lang.Math.pow;

/**
 * Polynomial
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
            result += coefficients[j] * pow(x, j);

        return result;
    }

    /**
     * Getter for degree.
     * @return int the degree.
     */
    public int degree() {
        return degree;
    }

    /**
     * Getter for coefficients
     * @return double[] the coefficients.
     */
    public double[] coefficients() {
        return coefficients;
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
