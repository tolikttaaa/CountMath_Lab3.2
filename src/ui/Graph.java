package ui;

import back.Function;
import back.exception.NotAllowedScopeException;
import back.exception.UnavailableCodeException;
import back.solution.Point;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Graph {
    private XYChart<Double, Double> graph;

    public Graph(final XYChart<Double, Double> graph) {
        this.graph = graph;
    }

    public void plotLine(final Function function, final double lowerBound, final double upperBound)
            throws NotAllowedScopeException, UnavailableCodeException {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (double x = lowerBound; x <= upperBound; x += (upperBound - lowerBound) / 1000d) {
            plotPoint(x, function.getValue(x), series);
        }

        graph.getData().add(series);
    }

    public void plotLine(List<Point> points) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (Point p : points) {
            plotPoint(p.first, p.second, series);
        }

        graph.getData().add(series);
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    public void clear() {
        graph.getData().clear();
    }

    public void plotPoint(final double x, final double y) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

        plotPoint(x, y, series);

        graph.getData().add(series);
    }
}