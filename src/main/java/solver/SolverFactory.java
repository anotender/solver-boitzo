package solver;

import problem.Problem;

public class SolverFactory {

    private final Problem problem;

    public SolverFactory(Problem problem) {
        this.problem = problem;
    }

    public Solver getSolver(String solver) {
        if ("dual".equals(solver)) {
            return new DualProblemSolver(problem);
        } else if ("simple".equals(solver)) {
            return new SimpleLinearProblemSolver(problem);
        }
        return null;
    }

}
