package set;

public class Set {

    protected double x[]; // input variables

    protected double min, max; // domain


    public Set(double[] vector, double domainMin, double domainMax) {
        if (domainMin >= domainMax) throw new IllegalArgumentException("Domain's min can't be greater than domain's max");

        this.x = vector;
        this.min = domainMin;
        this.max = domainMax;
    }

    public double[] vector() {
        return x;
    }

    public int size() {
        return x.length;
    }

    public double domainMin() {
        return min;
    }

    public double domainMax() {
        return max;
    }

    @Override
    public String toString() {
        return "Set{" +
                "size=" + x.length +
                "range=[" + min + ", " + max + "]}";
    }
}
