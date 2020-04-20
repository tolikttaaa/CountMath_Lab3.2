package ui;

import back.Function;
import back.Functions;
import back.exception.InvalidValueException;
import back.exception.NotAllowedScopeException;
import back.exception.NotImplementedSolutionException;
import back.exception.UnavailableCodeException;
import back.solution.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final double EPS = 1e-9;

    @FXML
    private Label helpPane;

    @FXML
    private VBox intPane;

    @FXML
    private AnchorPane result;

    @FXML
    private Label error;

    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Double, Double> chart;
    private Graph mathGraph;

    @FXML
    private ChoiceBox<InterpolationMethodType> intMethod;

    @FXML
    private ChoiceBox<Function> intFunction;

    @FXML
    private TextField intLeftBound;

    private double getLeftBound() throws InvalidValueException {
        try {
            return Double.parseDouble(intLeftBound.getText());
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Invalid left bound value!!!");
        }
    }

    @FXML
    private TextField intRightBound;

    private double getRightBound() throws InvalidValueException {
        try {
            return Double.parseDouble(intRightBound.getText());
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Invalid right bound value!!!");
        }
    }

    @FXML
    private TextField intPointCount;

    private int getCount() throws InvalidValueException {
        try {
            return Integer.parseInt(intPointCount.getText());
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Invalid points count value!!!");
        }
    }

    @FXML
    private ChoiceBox<Point> intPoints;

    @FXML
    private TextField pointYCoord;

    private double getPointYCoord() throws InvalidValueException {
        try {
            return Double.parseDouble(pointYCoord.getText());
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Invalid Y value!!!");
        }
    }

    @FXML
    private TextField resultXCoord;

    private double getResultXCoord() throws InvalidValueException {
        try {
            return Double.parseDouble(resultXCoord.getText());
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Invalid X value!!!");
        }
    }

    @FXML
    private TextField resultYCoord;

    private Function resultFunction;

    private InterpolationSolver interpolationSolver;

    @FXML
    private void setVisibleHelpPane() {
        helpPane.setVisible(true);
        intPane.setVisible(false);
        error.setVisible(false);
        result.setVisible(false);

        mathGraph.clear();
    }

    @FXML
    private void setVisibleIntPane() {
        helpPane.setVisible(false);
        intPane.setVisible(true);
        error.setVisible(true);

        mathGraph.clear();

        intMethod.setValue(InterpolationMethodType.NEWTON_POLYNOMIAL);
        intFunction.setValue(Functions.FUNCTION_2);

        setDefaultValues();
    }

    @FXML
    private void generatePoints() {
        clearError();
        result.setVisible(false);
        pointYCoord.setText("");

        try {
            validateValues();
        } catch (InvalidValueException e) {
            error.setText(e.getMessage());
            return;
        }

        try {
            ArrayList<Double> xValues = intMethod.getValue().generate(getLeftBound(), getRightBound(), getCount());
            ArrayList<Point> points = new ArrayList<>();

            for (double xValue : xValues) {
                points.add(new Point(xValue, intFunction.getValue().getValue(xValue)));
            }

            ObservableList<Point> observablePoints = FXCollections.observableArrayList(points);
            setPointsList(observablePoints);
            updateIntChart();
        } catch (
                NotImplementedSolutionException |
                NotAllowedScopeException |
                UnavailableCodeException |
                InvalidValueException e
        ) {
            result.setVisible(false);
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void intCalculate() {
        try {
            if (intPoints.getItems().isEmpty()) {
                throw new InvalidValueException("Please generate points before calculating");
            }

            resultFunction = interpolationSolver.solveInterpolation(intMethod.getValue(), intPoints.getItems());

            resultXCoord.setText("0");
            updateResultYCoord();
            result.setVisible(true);

            updateIntChart();
        } catch (Exception e) {
            result.setVisible(false);
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void updateIntChart() {
        try {
            mathGraph.clear();

            drawFunction();
            drawPoints();

            if (result.isVisible()) {
                drawResultFunction();
                drawResultPoint();
            }

//            xAxis.setAutoRanging(false);
//            yAxis.setAutoRanging(true);
//
//            double step = (getRightBound() - getLeftBound()) / 10;
//            xAxis.setLowerBound(getLeftBound() - step);
//            xAxis.setUpperBound(getRightBound() + step);
//            xAxis.setTickUnit(step);
        } catch (Exception e) {
            error.setText(e.getMessage());
        }
    }

    private void drawFunction() throws InvalidValueException, UnavailableCodeException, NotAllowedScopeException {
        mathGraph.plotLine(
                intFunction.getValue(),
                getLeftBound(),
                getRightBound()
        );
    }

    private void drawPoints() {
        mathGraph.plotLine(intPoints.getItems());
    }

    private void drawResultFunction() throws InvalidValueException, UnavailableCodeException, NotAllowedScopeException {
        mathGraph.plotLine(
                resultFunction,
                getLeftBound(),
                getRightBound()
        );
    }

    private void drawResultPoint() throws InvalidValueException, UnavailableCodeException, NotAllowedScopeException {
        mathGraph.plotPoint(getResultXCoord(), resultFunction.getValue(getResultXCoord()));
    }

    @FXML
    private void updatePoint() {
        try {
            ObservableList<Point> points = intPoints.getItems();
            Point curPoint = intPoints.getValue();

            points.remove(curPoint);
            curPoint.setY(getPointYCoord());
            points.add(curPoint);

            intCalculate();
            updateIntChart();
        } catch (Exception e) {
            error.setText(e.getMessage());
        }
    }

    private void intInit() {
        ObservableList<InterpolationMethodType> intMethods
                = FXCollections.observableArrayList(
                InterpolationMethodType.LAGRANGE_POLYNOMIAL,
                InterpolationMethodType.NEWTON_POLYNOMIAL,
                InterpolationMethodType.CUBIC_SPLINE
        );
        intMethod.setItems(intMethods);
        intMethod.setValue(InterpolationMethodType.NEWTON_POLYNOMIAL);

        ObservableList<Function> intFunctions = FXCollections.observableArrayList(
                Functions.FUNCTION_1,
                Functions.FUNCTION_2,
                Functions.FUNCTION_3
        );
        intFunction.setItems(intFunctions);
        intFunction.setValue(Functions.FUNCTION_2);

        setDefaultValues();

        intMethod.getSelectionModel().
                selectedItemProperty().
                addListener(
                (observable, oldValue, newValue) -> {
                    setDefaultValues();
                    generatePoints();
                    updateIntChart();
                });

        intFunction.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setDefaultValues();
                    generatePoints();
                    updateIntChart();
                });

        intLeftBound.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
                intLeftBound.setText(oldValue);
            } else {
                generatePoints();
            }
        });

        intRightBound.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
                intRightBound.setText(oldValue);
            } else {
                generatePoints();
            }
        });

        intPointCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}")) {
                intPointCount.setText(oldValue);
            } else {
                generatePoints();
            }
        });

        intPoints.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        pointYCoord.setText(String.format("%.4f", newValue.second).replace(',', '.'));
                    } catch (NullPointerException e) {
                        pointYCoord.setText("");
                    }
                });

        resultXCoord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
                intPointCount.setText(oldValue);
            } else {
                clearError();

                try {
                    updateResultYCoord();
                    updateIntChart();
                } catch (InvalidValueException | UnavailableCodeException | NotAllowedScopeException e) {
                    error.setText(e.getMessage());
                }
            }
        });
    }

    private void updateResultYCoord()
            throws InvalidValueException, UnavailableCodeException, NotAllowedScopeException {
        resultYCoord.setText(String.format("%.4f", resultFunction.getValue(getResultXCoord())).replace(',', '.'));
    }

    private void validateValues() throws InvalidValueException {
        if (getRightBound() - getLeftBound() < EPS) {
            throw new InvalidValueException("Left bound should be less than right bound.");
        }

        if (getCount() < 2) {
            throw new InvalidValueException("Count of Points should be more or equal than 2");
        }

        if (getCount() > 20) {
            throw new InvalidValueException("Count of Points should be less or equal than 20");
        }
    }

    private void setDefaultValues() {
        intLeftBound.setText("0");
        intRightBound.setText("9");
        intPointCount.setText("10");
        pointYCoord.setText("");

        clearError();
        result.setVisible(false);

        generatePoints();
    }

    private void setPointsList(ObservableList<Point> points) {
        intPoints.setItems(points);
    }

    private void clearError() {
        error.setText("");
    }

    private void chartInit() {
        mathGraph = new Graph(chart);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intInit();
        chartInit();
        setVisibleHelpPane();
        interpolationSolver = new InterpolationSolver();
    }
}
