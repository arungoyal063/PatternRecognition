package visualization;

import visualization.model.Figure;

import static visualization.Plotter.DIR_PLOTS;
import static visualization.Plotter.SEP;

/**
 *
 * @author Ondrej Kratochvil
 */
public class Plot {

    private String filename;
    private Figure figure;
    private boolean auto_open = false;

    public Plot(Figure figure, String filename) {
        this.figure = figure;
        this.filename = System.getProperty("user.dir") + SEP + DIR_PLOTS + SEP + filename + ".html";
    }

    public Plot autoOpen(boolean autoOpen) {
        this.auto_open = autoOpen;
        return this;
    }

    boolean isReady() {
        return figure.containsData();
    }
}
