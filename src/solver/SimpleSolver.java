package solver;

import exceptions.WrongFileFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class SimpleSolver implements Solver {

    private final String delimiter = ";";

    private String goal;
    private int[] goalFunctionVariablesFactors;
    private LinkedList<int[]> problemVariableFactors = new LinkedList<>();
    private LinkedList<Integer> problemLimits = new LinkedList<>();
    private LinkedList<String> inequalitySings = new LinkedList<>();
    private int[] lowerVariableLimits;
    private int[] upperVariableLimits;

    public SimpleSolver(String path) throws FileNotFoundException, WrongFileFormat {
        loadData(path);
    }

    public void solve() {

    }

    private void loadData(String path) throws FileNotFoundException, WrongFileFormat {
        File file = new File(path);
        try (Scanner in = new Scanner(file)) {
            String line;

            //get goal
            goal = in.nextLine();
            if (!goal.equals("max") && !goal.equals("min")) {
                throw new WrongFileFormat();
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
                throw new WrongFileFormat();
            }

            //get problemVariableFactors and problemLimits
            while (in.hasNextLine() && (line = in.nextLine()).split(delimiter).length == 4) {
                try {
                    String[] stringValues = line.split(delimiter);
                    problemVariableFactors.add(new int[]{
                            Integer.parseInt(stringValues[0]),
                            Integer.parseInt(stringValues[1])
                    });
                    problemLimits.add(Integer.parseInt(stringValues[2]));
                    if (!stringValues[3].equals("le") && !stringValues[3].equals("me")) {
                        throw new WrongFileFormat();
                    }
                    inequalitySings.add(stringValues[3]);
                } catch (NumberFormatException e) {
                    throw new WrongFileFormat();
                }
            }

            //get lowerVariableLimits and upperVariableLimits
            try {
                String[] stringValues = line.split(delimiter);
                lowerVariableLimits = new int[]{
                        Integer.parseInt(stringValues[0]),
                        Integer.parseInt(stringValues[1])
                };

                if (!in.hasNextLine()) {
                    throw new WrongFileFormat();
                }
                line = in.nextLine();
                stringValues = line.split(delimiter);
                upperVariableLimits = new int[]{
                        Integer.parseInt(stringValues[0]),
                        Integer.parseInt(stringValues[1])
                };
            } catch (NumberFormatException e) {
                throw new WrongFileFormat();
            }
        }
    }
}