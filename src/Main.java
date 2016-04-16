
import exceptions.WrongFileFormatException;
import problem.DualProblem;
import problem.SimpleLinearProblem;
import solver.DualProblemSolver;
import solver.SimpleLinearProblemSolver;
import solver.Solver;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                SimpleLinearProblem problem = new SimpleLinearProblem(args[0]);
                Solver solver = new SimpleLinearProblemSolver(problem);
                double[] result = solver.solve();
                System.out.println("[" + result[0] + " " + result[1] + "]");
            } catch (FileNotFoundException | WrongFileFormatException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } else if (args.length == 2 && args[0].equals("dual")) {
            try {
                DualProblem problem = new DualProblem(args[1]);
                Solver solver = new DualProblemSolver(problem);
                double[] result = solver.solve();
                System.out.println("[" + result[0] + " " + result[1] + "]");
            } catch (FileNotFoundException | WrongFileFormatException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong arguments");
        }
    }
}
