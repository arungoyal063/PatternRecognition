package functions;

/**
 * @author Ondrej Kratochvil
 */
public class CustomFunction extends Function {

    private Image comp;
    private String name;

    public CustomFunction(Image comp) {
        this(comp, "custom function");
    }

    public CustomFunction(Image comp, String name) {
        this.comp = comp;
        this.name = name;
    }

    @Override
    public double f(double x) {
        return comp.compute(x);
    }

    @Override
    public String name() {
        return name;
    }
}
