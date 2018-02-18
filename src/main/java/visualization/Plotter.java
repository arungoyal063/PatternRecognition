package visualization;

import com.google.gson.Gson;

import java.io.*;


/**
 * Plots using plotly via python script
 * @author Ondrej Kratochvil
 */
public class Plotter {

    private static System.Logger LOGGER = System.getLogger(Plotter.class.getName());

    private static final String PLOTLY_SCRIPT = "src/main/python/Visualize.py";
    private static final String DIR_QUEUE = "plots/queue";
    static final String DIR_PLOTS = "plots";
    static final char SEP = '/';

    private static final Gson GSON = new Gson();


    public void plot(Plot plot) {
        // checks if plot contains data
        if (!plot.isReady()) throw new IllegalArgumentException("Plot doesn't contains all necessary data");

        // make sure dirs exists
        checkDirs();

        // enqueue plot
        File queuedPlot = new File(DIR_QUEUE + SEP + String.valueOf(System.currentTimeMillis()));
        BufferedWriter w = null;

        try {
            // write data
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(queuedPlot)));
            w.write(GSON.toJson(plot));
            w.close();

            try {
                // exec plotly
                Runtime.getRuntime().exec("python " +  PLOTLY_SCRIPT + " " + queuedPlot.getAbsolutePath());
            } catch (IOException ex){
                LOGGER.log(System.Logger.Level.ERROR, ex.getMessage());
            }

        } catch (IOException ex) {
            LOGGER.log(System.Logger.Level.ERROR, ex.getMessage());
        } finally {
            try {w.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    private void checkDirs() {
        File dirPlot = new File(DIR_PLOTS);
        if (!dirPlot.exists()) dirPlot.mkdir();

        File dirQueue = new File(DIR_QUEUE);
        if (!dirQueue.exists()) dirQueue.mkdir();
    }
}
