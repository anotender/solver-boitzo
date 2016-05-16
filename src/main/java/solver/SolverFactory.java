package solver;

import problem.Problem;

public class SolverFactory {

    private final Problem problem1;
    private final Problem problem2;

    public SolverFactory(Problem problem) {
        this.problem1 = problem;
        this.problem2 = null;
    }

    public SolverFactory(Problem numeratorProblem, Problem denominatorProblem) {
        this.problem1 = numeratorProblem;
        this.problem2 = denominatorProblem;
    }

    public Solver getSolver(String solver) {
        if ("dual".equals(solver)) {
            return new DualProblemSolver(problem1);
        } else if ("simple".equals(solver)) {
            return new SimpleLinearProblemSolver(problem1);
        } else if ("quotient".equals(solver)) {
            return new QuotientProblemSolver(problem1, problem2);
        }
        return null;
    }

}
