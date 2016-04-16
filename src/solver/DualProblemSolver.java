package solver;

import problem.DualProblem;

public class DualProblemSolver implements Solver {

    private DualProblem problem;
    private DualProblem reversedProblem;

    public DualProblemSolver(DualProblem problem) {
        this.problem = problem;
        this.reversedProblem = problem.reverse();
    }

    public double[] solve() {
        return new double[2];
    }
}