package solver;

import problem.Problem;

import java.util.Comparator;

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
                .max(Comparator.comparingDouble(this::countQuotientGoalFunctionValue))
                .orElseThrow(() -> new RuntimeException("Cannot find max value"));
    }

    public double countQuotientGoalFunctionValue(double[] point) {
        return numeratorProblem.getGoalFunction().countValue(point) / denominatorProblem.getGoalFunction().countValue(point);
    }
}
