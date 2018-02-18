package visualization.model;

/**
 *
 * @author Ondrej Kratochvil
 */
public class Scatter {

    private boolean visible;
    private String name;
    private double[] x;
    private double[] y;

    public Scatter(String name, double[] x, double[] y) {
        if (x.length != y.length) throw new IllegalArgumentException("x.length and y.length can't differ");

        this.name = name;
        this.x = x;
        this.y = y;
        this.visible = true;
    }
}
