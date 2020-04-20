package back.solution;

import back.exception.NotImplementedSolutionException;

import java.util.ArrayList;

public interface DotGenerator {
    ArrayList<Double> generate(double leftBound, double rightBound, int count)
            throws NotImplementedSolutionException;

    default ArrayList<Double> generate(double leftBound, double rightBound)
            throws NotImplementedSolutionException {
        return generate(leftBound, rightBound, 10);
    }
}
