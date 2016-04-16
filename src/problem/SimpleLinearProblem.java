package problem;

import exceptions.WrongFileFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class SimpleLinearProblem {
    private final String delimiter = ";";

    private String goal;
    private double[] goalFunctionVariablesFactors;
    private LinkedList<double[]> variablesFactors = new LinkedList<>();
    private LinkedList<Double> limits = new LinkedList<>();
    private LinkedList<String> inequalitySigns = new LinkedList<>();

    public SimpleLinearProblem(String path) throws FileNotFoundException, WrongFileFormatException {
        loadData(path);
    }

    public String getGoal() {
        return goal;
    }

    public double[] getGoalFunctionVariablesFactors() {
        return goalFunctionVariablesFactors;
    }

    public LinkedList<double[]> getVariablesFactors() {
        return variablesFactors;
    }

    public LinkedList<Double> getLimits() {
        return limits;
    }

    public LinkedList<String> getInequalitySigns() {
        return inequalitySigns;
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
                        Double.parseDouble(stringFactors[1])
                };
            } catch (NumberFormatException e) {
                throw e;
            }

            //get variablesFactors and limits
            while (in.hasNextLine()) {
                try {
                    line = in.nextLine();
                    String[] stringValues = line.split(delimiter);
                    if (stringValues.length != 4) {
                        throw new WrongFileFormatException();
                    }
                    variablesFactors.add(new double[]{
                            Double.parseDouble(stringValues[0]),
                            Double.parseDouble(stringValues[1])
                    });
                    limits.add(Double.parseDouble(stringValues[2]));
                    if (!stringValues[3].equals("le") && !stringValues[3].equals("ge")) {
                        throw new WrongFileFormatException();
                    }
                    inequalitySigns.add(stringValues[3]);
                } catch (NumberFormatException e) {
                    throw e;
                }
            }
        }
    }
}
