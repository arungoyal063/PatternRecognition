package examples;

import functions.CustomFunction;
import functions.Function;
import polynomial_curve_fitting.BasicPolynomialCurveFitting;
import set.TrainingSet;
import utils.Utils;
import visualization.*;
import visualization.model.*;

/**
 * Example for BasicPolynomialCurveFitting
 * @author Ondrej Kratochvil
 */
public class BasicPolynomialCurveFittingExample {

    private static final int TRAINING_SET_SIZE = 200;

    private static final double DOMAIN_MIN = -10;
    private static final double DOMAIN_MAX = 10;
    private static final int DOMAIN_DENSITY = 1000;

    public static void main(String[] args) {

        // create function to be analysed
        Function function = new CustomFunction(Math::sin);

        // create random training set
        TrainingSet set = TrainingSet.random(function, TRAINING_SET_SIZE, DOMAIN_MIN, DOMAIN_MAX);

        // use BasicPolynomialCurveFitting class to find optimal polynomial
        Function polynomial = new BasicPolynomialCurveFitting(set.vector(), set.targets()).getPolynomial();

        // plot both original function and its curve estimation using python and plotly
        // get domain for plot
        double[] domain = Utils.randomSortedArray(DOMAIN_DENSITY, DOMAIN_MIN, DOMAIN_MAX);

        // creates traces for both functions
        Scatter traceSin = new Scatter(function.name(), domain, function.f(domain));
        Scatter tracePol = new Scatter(polynomial.name(), domain, polynomial.f(domain));

        // plot figure
        Figure fig = new Figure(new Layout("Awesome title")).add(traceSin).add(tracePol);
        new Plotter().plot(new Plot(fig, "curve-fitting-for-" + function.name()).autoOpen(true));
    }
}
