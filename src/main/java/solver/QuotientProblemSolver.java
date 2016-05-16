package solver;

import problem.Problem;

public class QuotientProblemSolver implements Solver {

    private Problem numeratorProblem;
    private Problem denominatorProblem;

    public QuotientProblemSolver(Problem numeratorProblem, Problem denominatorProblem) {
        this.numeratorProblem = numeratorProblem;
        this.denominatorProblem = denominatorProblem;
    }

    @Override
    public double[] solve() {
        return findSolution();
    }

    private double[] findSolution() {
        return new SimpleLinearProblemSolver(numeratorProblem)
                .findSuspiciousPoints()
                .stream()
                .max((p1, p2) -> Double.compare(countQuotientGoalFunctionValue(p1), countQuotientGoalFunctionValue(p2)))
                .get();
    }

    public double countQuotientGoalFunctionValue(double[] point) {
        return numeratorProblem.getGoalFunction().countValue(point) / denominatorProblem.getGoalFunction().countValue(point);
    }
}
