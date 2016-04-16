package problem;

import exceptions.WrongFileFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DualProblem {
    private final String delimiter = ";";

    private String goal;
    private double[] goalFunctionVariablesFactors;
    private double[][] variablesFactors;
    private double[] limits;
    private String[] inequalitySigns;

    private DualProblem() {
    }

    public DualProblem(String path) throws FileNotFoundException, WrongFileFormatException {
        loadData(path);
    }

    public String getGoal() {
        return goal;
    }

    public double[] getGoalFunctionVariablesFactors() {
        return goalFunctionVariablesFactors;
    }

    public double[][] getVariablesFactors() {
        return variablesFactors;
    }

    public double[] getLimits() {
        return limits;
    }

    public String[] getInequalitySigns() {
        return inequalitySigns;
    }

    public DualProblem reverse() {
        DualProblem reversedProblem = new DualProblem();

        //change goal
        reversedProblem.goal = goal.equals("max") ? "min" : "max";

        //change goal function variables factors
        reversedProblem.goalFunctionVariablesFactors = Arrays.copyOf(limits, limits.length);

        //change variables factors
        int rows = variablesFactors[0].length;
        int cols = variablesFactors.length;
        reversedProblem.variablesFactors = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                reversedProblem.variablesFactors[i][j] = variablesFactors[j][i];
            }
        }

        //change limits
        reversedProblem.limits = Arrays.copyOf(goalFunctionVariablesFactors, goalFunctionVariablesFactors.length);

        //change inequality signs
        String sign = inequalitySigns[0].equals("le") ? "ge" : "le";
        reversedProblem.inequalitySigns = new String[3];
        Arrays.fill(reversedProblem.inequalitySigns, sign);

        return reversedProblem;
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
                goalFunctionVariablesFactors = new double[]{
                        Double.parseDouble(stringFactors[0]),
                        Double.parseDouble(stringFactors[1]),
                        Double.parseDouble(stringFactors[2])
                };
            } catch (NumberFormatException e) {
                throw e;
            }

            //get variablesFactors and limits
            try {
                variablesFactors = new double[2][];
                limits = new double[2];
                inequalitySigns = new String[2];
                for (int i = 0; i < 2; i++) {
                    line = in.nextLine();
                    String[] stringValues = line.split(delimiter);
                    if (stringValues.length != 5) {
                        throw new WrongFileFormatException();
                    }
                    variablesFactors[i] = new double[]{
                            Double.parseDouble(stringValues[0]),
                            Double.parseDouble(stringValues[1]),
                            Double.parseDouble(stringValues[2])
                    };
                    limits[i] = Double.parseDouble(stringValues[3]);
                    if (!stringValues[4].equals("le") && !stringValues[4].equals("ge")) {
                        throw new WrongFileFormatException();
                    }
                    inequalitySigns[i] = stringValues[4];
                }
            } catch (NumberFormatException e) {
                throw e;
            }

        }
    }
}
