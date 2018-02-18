package examples;

import functions.CustomFunction;
import functions.Function;
import polynomial_curve_fitting.LeastSquaresPCF;
import set.TrainingSet;
import utils.Utils;
import visualization.*;
import visualization.model.*;

import static java.lang.Math.*;

/**
 * Example for LeastSquaresPCF
 * @author Ondrej Kratochvil
 */
public class Example_LeastSquaresPCF {

    private static final int TRAINING_SET_SIZE = 100;

    private static final double DOMAIN_MIN = -1000;
    private static final double DOMAIN_MAX = 1000;
    private static final int DOMAIN_DENSITY = 10000;

    public static void main(String[] args) {

        // create function to be analysed
        Function function = new CustomFunction(x -> sqrt(abs(x)), "test");

        // create random training set
        TrainingSet set = TrainingSet.random(function, TRAINING_SET_SIZE, DOMAIN_MIN, DOMAIN_MAX);

        // use LeastSquaresPCF class to find optimal polynomial
        Function polynomial = new LeastSquaresPCF(set.vector(), set.targets()).polynomial();

        // plot both original function and its curve estimation using python and plotly
        // get domain for plot
        double[] domain = Utils.randomSortedArray(DOMAIN_DENSITY, DOMAIN_MIN, DOMAIN_MAX);

        // creates traces for both functions
        Scatter traceSin = new Scatter(function.name(), domain, function.f(domain));
        Scatter tracePol = new Scatter(polynomial.name(), domain, polynomial.f(domain));

        // plot figure
        Figure fig = new Figure(new Layout("Awesome title")).add(traceSin).add(tracePol);
        new Plotter().plot(new Plot(fig, "curve-fitting-" + function.name()).autoOpen(true));
    }
}