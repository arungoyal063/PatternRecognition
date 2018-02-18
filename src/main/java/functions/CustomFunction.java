package functions;

/**
 * Custom function.
 * @author Ondrej Kratochvil
 */
public class CustomFunction extends Function {

    private Image img;
    private String name;

    public CustomFunction(Image img) {
        this(img, "custom function");
    }

    public CustomFunction(Image img, String name) {
        this.img = img;
        this.name = name;
    }

    @Override
    public double f(double x) {
        return img.compute(x);
    }

    @Override
    public String name() {
        return name;
    }
}
