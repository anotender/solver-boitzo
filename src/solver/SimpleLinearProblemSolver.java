package solver;

import problem.SimpleLinearProblem;

import java.util.Collections;
import java.util.LinkedList;

public class SimpleLinearProblemSolver implements Solver {

    private SimpleLinearProblem problem;

    public SimpleLinearProblemSolver(SimpleLinearProblem problem) {
        this.problem = problem;
    }

    public double[] solve() {
        //find all suspicious points
        LinkedList<double[]> points = findSuspiciousPoints();

        //count goal function values for suspicious points
        LinkedList<Double> goalFunctionValues = countGoalFunctionValues(points);

        //find and return the solution
        return findSolution(points, goalFunctionValues);
    }

    private double[] findSolution(LinkedList<double[]> points, LinkedList<Double> goalFunctionValues) {
        if (problem.getGoal().equals("max")) {
            double maxValue = Collections.max(goalFunctionValues);
            int index = goalFunctionValues.indexOf(maxValue);
            return points.get(index);
        } else {
            double minValue = Collections.min(goalFunctionValues);
            int index = goalFunctionValues.indexOf(minValue);
            return points.get(index);
        }
    }

    private LinkedList<double[]> findSuspiciousPoints() {
        LinkedList<double[]> result = new LinkedList<>();

        for (int i = 0; i < problem.getVariablesFactors().size(); i++) {
            for (int j = i + 1; j < problem.getVariablesFactors().size(); j++) {
                double[][] a = new double[][]{
                        problem.getVariablesFactors().get(i),
                        problem.getVariablesFactors().get(j)
                };
                double[] b = new double[]{
                        problem.getLimits().get(i),
                        problem.getLimits().get(j),
                };
                LinearEquationSolver linearEquationSolver = new LinearEquationSolver(a, b);
                double[] point = linearEquationSolver.solve();
                if (point != null) {
                    result.add(point);
                }
            }
        }

        return result;
    }

    private LinkedList<Double> countGoalFunctionValues(LinkedList<double[]> points) {
        LinkedList<Double> values = new LinkedList<>();

        for (double[] point : points) {
            if (meetsAllConditions(point)) {
                double value = countGoalFunctionValue(point);
                values.add(value);
            }

        }

        return values;
    }

    private double countGoalFunctionValue(double[] point) {
        return problem.getGoalFunctionVariablesFactors()[0] * point[0] +
                problem.getGoalFunctionVariablesFactors()[1] * point[1];
    }

    private boolean meetsAllConditions(double[] point) {
        for (int i = 0; i < problem.getLimits().size(); i++) {
            double a = problem.getVariablesFactors().get(i)[0];
            double b = problem.getVariablesFactors().get(i)[1];
            double value = a * point[0] + b * point[1];

            if (problem.getInequalitySigns().get(i).equals("le")) {
                if (value > problem.getLimits().get(i)) {
                    return false;
                }
            } else {
                if (value < problem.getLimits().get(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}