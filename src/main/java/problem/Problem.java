package problem;

import problem.condition.Condition;
import problem.function.GoalFunction;

import java.util.LinkedList;

public class Problem {
    private LinkedList<Condition> conditions;
    private GoalFunction goalFunction;

    public LinkedList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(LinkedList<Condition> conditions) {
        this.conditions = conditions;
    }

    public GoalFunction getGoalFunction() {
        return goalFunction;
    }

    public void setGoalFunction(GoalFunction goalFunction) {
        this.goalFunction = goalFunction;
    }

    public boolean meetsAllConditions(double[] point) {
        for (Condition c : conditions) {
            if (!c.meetsCondition(point)) {
                return false;
            }
        }
        return true;
    }

    public Problem reverse() {
        //set up reversed function
        GoalFunction reversedGoalFunction = new GoalFunction();

        String goal = "max".equals(goalFunction.getGoal()) ? "min" : "max";
        reversedGoalFunction.setGoal(goal);

        double[] factors = new double[conditions.size()];
        for (int i = 0; i < factors.length; i++) {
            factors[i] = conditions.get(i).getLimit();
        }
        reversedGoalFunction.setFactors(factors);

        //set up reversed conditions
        int rows = conditions.get(0).getFactors().length;
        int cols = conditions.size();
        LinkedList<Condition> reversedConditions = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            Condition condition = new Condition();
            double[] newFactors = new double[2];
            String sign = null;
            for (int j = 0; j < cols; j++) {
                newFactors[j] = conditions.get(j).getFactors()[i];
                sign = "<=".equals(conditions.get(j).getSign()) ? ">=" : "<=";
            }
            condition.setFactors(newFactors);
            condition.setLimit(goalFunction.getFactors()[i]);
            condition.setSign(sign);
            reversedConditions.add(condition);
        }


        Problem reversedProblem = new Problem();
        reversedProblem.setGoalFunction(reversedGoalFunction);
        reversedProblem.setConditions(reversedConditions);
        return reversedProblem;
    }
}
