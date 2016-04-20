package solver;

import problem.Problem;
import problem.condition.Condition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleLinearProblemSolver implements Solver {

    private Problem problem;

    public SimpleLinearProblemSolver(Problem problem) {
        this.problem = problem;
    }

    @Override
    public double[] solve() {
        //find all suspicious points
        List<double[]> points = findSuspiciousPoints();

        //count goal function values
        List<Double> values = countGoalFunctionValues(points);

        //find and return the solution
        return findSolution(points, values);
    }

    private double[] findSolution(List<double[]> points, List<Double> values) {
        if ("max".equals(problem.getGoalFunction().getGoal())) {
            double maxValue = Collections.max(values);
            int index = values.indexOf(maxValue);
            return points.get(index);
        } else {
            double maxValue = Collections.min(values);
            int index = values.indexOf(maxValue);
            return points.get(index);
        }
    }

    private List<Double> countGoalFunctionValues(List<double[]> points) {
        return points
                .stream()
                .map(point -> problem.getGoalFunction().countValue(point))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<double[]> findSuspiciousPoints() {
        LinkedList<double[]> points = new LinkedList<>();

        for (int i = 0; i < problem.getConditions().size(); i++) {
            Condition cond1 = problem.getConditions().get(i);
            for (int j = i + 1; j < problem.getConditions().size(); j++) {
                Condition cond2 = problem.getConditions().get(j);
                double[][] a = new double[][]{
                        cond1.getFactors(),
                        cond2.getFactors()
                };
                double[] b = new double[]{
                        cond1.getLimit(),
                        cond2.getLimit()
                };
                LinearEquationSolver linearEquationSolver = new LinearEquationSolver(a, b);
                double[] point = linearEquationSolver.solve();
                if (point != null && problem.meetsAllConditions(point)) {
                    points.add(point);
                }
            }
        }

        return points;
    }
}