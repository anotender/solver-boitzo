package solver;

import exceptions.WrongFileFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class SimpleLinearProblemSolver implements Solver {

    private final String delimiter = ";";

    private String goal;
    private int[] goalFunctionVariablesFactors;
    private LinkedList<int[]> problemVariableFactors = new LinkedList<>();
    private LinkedList<Integer> problemLimits = new LinkedList<>();
    private LinkedList<String> inequalitySings = new LinkedList<>();

    public SimpleLinearProblemSolver(String path) throws FileNotFoundException, WrongFileFormatException, NumberFormatException {
        loadData(path);
    }

    public double[] solve() {
        LinkedList<double[]> points = new LinkedList<>();
        LinkedList<Double> goalFunctionValues = new LinkedList<>();

        //find all suspicious points
        for (int i = 0; i < problemVariableFactors.size(); i++) {
            for (int j = i + 1; j < problemVariableFactors.size(); j++) {
                int[][] a = new int[][]{
                        problemVariableFactors.get(i),
                        problemVariableFactors.get(j)
                };
                int[] b = new int[]{
                        problemLimits.get(i),
                        problemLimits.get(j),
                };
                LinearEquationSolver linearEquationSolver = new LinearEquationSolver(a, b);
                double[] result = linearEquationSolver.solve();
                if (result != null) {
                    points.add(result);
                }
            }
        }

        //count goal function values for result points
        for (double[] point : points) {
            if (meetsAllConditions(point)) {
                double value = countGoalFunctionValue(point);
                goalFunctionValues.add(value);
            }
        }

        //check which one is the solution
        if (goal.equals("max")) {
            double maxValue = Collections.max(goalFunctionValues);
            int index = goalFunctionValues.indexOf(maxValue);
            return points.get(index);
        } else {
            double minValue = Collections.min(goalFunctionValues);
            int index = goalFunctionValues.indexOf(minValue);
            return points.get(index);
        }
    }

    private boolean meetsAllConditions(double[] point) {
        for (int i = 0; i < problemLimits.size(); i++) {
            int a = problemVariableFactors.get(i)[0];
            int b = problemVariableFactors.get(i)[1];
            double value = a * point[0] + b * point[1];

            if (inequalitySings.get(i).equals("le")) {
                if (value > problemLimits.get(i)) {
                    return false;
                }
            } else {
                if (value < problemLimits.get(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private double countGoalFunctionValue(double[] point) {
        return goalFunctionVariablesFactors[0] * point[0] + goalFunctionVariablesFactors[1] * point[1];
    }

    private void loadData(String path) throws FileNotFoundException, WrongFileFormatException {
        File file = new File(path);
        try (Scanner in = new Scanner(file)) {
            String line;

            //get goal
            goal = in.nextLine();
            if (!goal.equals("max") && !goal.equals("min")) {
                throw new WrongFileFormatException();
            }

            //get goalFunctionVariablesFactors
            line = in.nextLine();
            String[] stringFactors = line.split(delimiter);
            try {
                goalFunctionVariablesFactors = new int[]{
                        Integer.parseInt(stringFactors[0]),
                        Integer.parseInt(stringFactors[1])
                };
            } catch (NumberFormatException e) {
                throw e;
            }

            //get problemVariableFactors and problemLimits
            while (in.hasNextLine()) {
                try {
                    line = in.nextLine();
                    String[] stringValues = line.split(delimiter);
                    if (stringValues.length != 4) {
                        throw new WrongFileFormatException();
                    }
                    problemVariableFactors.add(new int[]{
                            Integer.parseInt(stringValues[0]),
                            Integer.parseInt(stringValues[1])
                    });
                    problemLimits.add(Integer.parseInt(stringValues[2]));
                    if (!stringValues[3].equals("le") && !stringValues[3].equals("ge")) {
                        throw new WrongFileFormatException();
                    }
                    inequalitySings.add(stringValues[3]);
                } catch (NumberFormatException e) {
                    throw e;
                }
            }
        }
    }
}