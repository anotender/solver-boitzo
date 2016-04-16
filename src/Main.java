
import exceptions.WrongFileFormatException;
import exceptions.WrongMatrixSizeException;
import solver.SimpleLinearProblemSolver;
import solver.Solver;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                Solver solver = new SimpleLinearProblemSolver(args[0]);
                double[] result = solver.solve();
                System.out.println("[" + result[0] + " " + result[1] + "]");
            } catch (FileNotFoundException | WrongFileFormatException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong number of arguments");
        }
    }
}
