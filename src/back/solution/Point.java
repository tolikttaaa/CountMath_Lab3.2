package back.solution;

import back.Pair;

public class Point extends Pair<Double, Double> implements Comparable<Point> {
    private static final double EPS = 1e-9d;

    /**
     * Constructor for a Pair.
     *
     * @param x  the first object in the Pair
     * @param y the second object in the Pair
     */

    public Point(Double x, Double y) {
        super(x, y);
    }

    public void setY(double y) {
        this.second = y;
    }

    @Override
    public String toString() {
        return String.format("( %.4f; %.4f )", first, second);
    }

    @Override
    public int compareTo(Point that) {
        if (Math.abs(that.first - this.first) < EPS) {
            return that.second - this.second < 0 ? 1 : -1;
        }

        return that.first - this.first < 0 ? 1 : -1;
    }
}
