package visualization.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Ondrej Kratochvil
 */
public class Figure {

    private List<Scatter> data;
    private Layout layout;


    public Figure(Layout layout) {
        this.layout = layout;
        data = new LinkedList<>();
    }

    public Figure add(Scatter trace) {
        data.add(trace);
        return this;
    }

    public Figure add(List<Scatter> traces) {
        data.addAll(traces);
        return this;
    }

    public boolean containsData() {
        return data.size() != 0;
    }
}
