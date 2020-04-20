package back.solution;

import back.DerivativeFunc;
import back.Interval;
import back.exception.NotImplementedSolutionException;

import java.util.Collections;
import java.util.List;

public class InterpolationSolver {
    public DerivativeFunc solveInterpolation(InterpolationMethodType type, List<Point> points)
            throws NotImplementedSolutionException {
        Collections.sort(points);

        switch (type) {
            case NEWTON_POLYNOMIAL:
                return solveByNewtonPolynomial(points);
            case LAGRANGE_POLYNOMIAL:
            case CUBIC_SPLINE:
            default:
                throw new NotImplementedSolutionException();
        }
    }

    private DerivativeFunc solveByNewtonPolynomial(List<Point> points) {
        int n = points.size();
        double[][] dividedDiff = new double[n][n];

        for (int i = 0; i < n; i++) {
            dividedDiff[0][i] = points.get(i).second;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                dividedDiff[i][j] = dividedDiff[i - 1][j + 1] - dividedDiff[i - 1][j];
            }
        }

        Point p0 = points.get(0);

        double h = points.get(1).first - p0.first;

        return new DerivativeFunc() {
            @Override
            public double get(double argument) {
                double res = p0.second;

                double q = (argument - p0.first) / h;
                double product = 1;

                for (int i = 1; i < n; i++) {
                    product *= q + 1 - i;
                    product /= i;

                    res += product * dividedDiff[i][0];
                }

                return res;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                return new Interval[0];
            }
        };
    }
}
