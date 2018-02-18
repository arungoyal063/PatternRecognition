package functions;

/**
 * Sin
 * @author Ondrej Kratochvil
 */
public class Sin extends Function {

    private final double a, b, c; // a*sin(b*x)+c

    public Sin(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Sin(double b) {
        this(1, b, 0);
    }

    public double f(double x) {
        return a * Math.sin(b * x) + c;
    }

    @Override
    public String name() {
        return "sin";
    }

    @Override
    public String toString() {
        return "sin(x) = " + a + " * sin(" + b + " * x) + " + c;
    }
}
