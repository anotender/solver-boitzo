package solver;

import problem.Problem;
import problem.condition.Condition;

public class DualProblemSolver implements Solver {
    private Problem primaryProblem;
    private Problem reversedProblem;

    public DualProblemSolver(Problem primaryProblem) {
        this.primaryProblem = primaryProblem;
        this.reversedProblem = primaryProblem.reverse();
    }

    @Override
    public double[] solve() {
        //solve reversed problem
        SimpleLinearProblemSolver simpleLinearProblemSolver = new SimpleLinearProblemSolver(reversedProblem);
        double[] point = simpleLinearProblemSolver.solve();

        //check which goal function factors can be zero in primary problem
        boolean[] isZeroInPrimaryProblem = new boolean[reversedProblem.getConditions().size()];
        int i = 0;
        for (Condition c : reversedProblem.getConditions()) {
            isZeroInPrimaryProblem[i++] = c.countInequalityValue(point) != c.getLimit();
        }

        //set new factors for goal function
        double[] oldFactors = primaryProblem.getGoalFunction().getFactors();
        double[] newFactors = new double[oldFactors.length];
        for (i = 0; i < newFactors.length; i++) {
            newFactors[i] = isZeroInPrimaryProblem[i] ? 0 : oldFactors[i];
        }
        primaryProblem.getGoalFunction().setFactors(newFactors);

        //find solution
        double[][] primaryProblemVariablesFactors = new double[primaryProblem.getConditions().size()][];
        for (i = 0; i < primaryProblemVariablesFactors.length; i++) {
            primaryProblemVariablesFactors[i] = primaryProblem.getConditions().get(i).getFactors();
        }

        double[][] a = new double[2][];
        for (i = 0; i < primaryProblemVariablesFactors.length; i++) {
            if (!isZeroInPrimaryProblem[i]) {
                a[i] = primaryProblemVariablesFactors[i];
            }
        }

        double[] b = new double[primaryProblem.getConditions().size()];
        for (i = 0; i < b.length; i++) {
            b[i] = primaryProblem.getConditions().get(i).getLimit();
        }

        LinearEquationSolver linearEquationSolver = new LinearEquationSolver(a, b);
        return linearEquationSolver.solve();
    }
}