
import com.fasterxml.jackson.databind.ObjectMapper;
import problem.Problem;
import solver.DualProblemSolver;
import solver.SimpleLinearProblemSolver;
import solver.Solver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Problem problem = mapper.readValue(new File(args[0]), Problem.class);
                Solver solver = new SimpleLinearProblemSolver(problem);
                double[] result = solver.solve();
                System.out.println(Arrays.toString(result) + " -> " + problem.getGoalFunction().countValue(result));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else if (args.length == 2 && args[0].equals("dual")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Problem problem = mapper.readValue(new File(args[1]), Problem.class);
                Solver solver = new DualProblemSolver(problem);
                double[] result = solver.solve();
                System.out.println(Arrays.toString(result) + " -> " + problem.getGoalFunction().countValue(result));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong arguments");
        }
    }
}
