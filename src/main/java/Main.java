import com.fasterxml.jackson.databind.ObjectMapper;
import problem.Problem;
import solver.QuotientProblemSolver;
import solver.Solver;
import solver.SolverFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Problem problem = mapper.readValue(new File(args[1]), Problem.class);
                SolverFactory solverFactory = new SolverFactory(problem);
                Solver solver = solverFactory.getSolver(args[0]);
                double[] result = solver.solve();
                System.out.println(Arrays.toString(result) + " -> " + problem.getGoalFunction().countValue(result));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else if (args.length == 3) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Problem numeratorProblem = mapper.readValue(new File(args[1]), Problem.class);
                Problem denominatorProblem = mapper.readValue(new File(args[2]), Problem.class);
                SolverFactory solverFactory = new SolverFactory(numeratorProblem, denominatorProblem);
                Solver solver = solverFactory.getSolver(args[0]);
                double[] result = solver.solve();
                System.out.println(Arrays.toString(result) + " -> " + ((QuotientProblemSolver) solver).countQuotientGoalFunctionValue(result));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong arguments");
        }
    }
}
