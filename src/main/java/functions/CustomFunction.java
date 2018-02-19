package functions;

/**
 * Custom function.
 * @author Ondrej Kratochvil
 */
public class CustomFunction extends Function {

    private Func func;
    private String name;

    public CustomFunction(Func func) {
        this(func, "custom function");
    }

    public CustomFunction(Func func, String name) {
        this.func = func;
        this.name = name;
    }

    @Override
    public double f(double x) {
        return func.compute(x);
    }

    @Override
    public String name() {
        return name;
    }
}
