package back.solution;

import back.exception.NotImplementedSolutionException;

import java.util.ArrayList;
import java.util.Random;

public enum InterpolationMethodType implements DotGenerator {
    LAGRANGE_POLYNOMIAL("Lagrange polynomial") {
        @Override
        public ArrayList<Double> generate(double leftBound, double rightBound, int count) {
            ArrayList<Double> res = new ArrayList<>();

            res.add(leftBound);
            res.add(rightBound);

            for (int i = 0; i < count; i++) {
                res.add(random.nextDouble() * (rightBound - leftBound) + leftBound);
            }

            return res;
        }
    },
    NEWTON_POLYNOMIAL("Newton polynomial") {
        @Override
        public ArrayList<Double> generate(double leftBound, double rightBound, int count) {
            ArrayList<Double> res = new ArrayList<>();

            double step = (rightBound - leftBound) / (count - 1);
            for (int i = 0; i < count; i++) {
                res.add(leftBound + i * step);
            }

            return res;
        }
    },
    CUBIC_SPLINE("Cubic Spline") {
        @Override
        public ArrayList<Double> generate(double leftBound, double rightBound, int count)
                throws NotImplementedSolutionException {
            throw new NotImplementedSolutionException();
        }
    };

    private static final double EPS = 1e-9d;

    private static final Random random = new Random(566);
    private String methodName;

    InterpolationMethodType(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return methodName;
    }
}
