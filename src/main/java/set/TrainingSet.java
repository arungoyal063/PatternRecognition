package set;

import functions.Function;
import utils.Utils;

public class TrainingSet extends Set {

    private double t[]; // corresponding targets

    public TrainingSet(double[] vector, double[] targets, double domainMin, double domainMax) {
        super(vector, domainMin, domainMax);

        if (vector.length != targets.length)
            throw new IllegalArgumentException("Vector and targets length are not same.");

        this.t = targets;
    }

    public static TrainingSet randomSorted(Function fun, int size, double domainMin, double domainMax) {
        double[] x = Utils.randomSortedVector(size, domainMin, domainMax);
        double[] t = fun.f(x);
        return new TrainingSet(x, t, domainMin, domainMax);
    }

    public static TrainingSet random(Function fun, int size, double domainMin, double domainMax) {
        double[] x = Utils.randomVector(size, domainMin, domainMax);
        double[] t = fun.f(x);
        return new TrainingSet(x, t, domainMin, domainMax);
    }

    public double[] targets() {
        return t;
    }
}