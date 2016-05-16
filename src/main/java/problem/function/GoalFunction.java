package problem.function;

public class GoalFunction {
    private String goal;
    private double[] factors;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public double[] getFactors() {
        return factors;
    }

    public void setFactors(double[] factors) {
        this.factors = factors;
    }

    public double countValue(double[] point) {
        double result = 0;
        for (int i = 0; i < point.length; i++) {
            result += point[i] * factors[i];
        }
        return result + factors[factors.length - 1];
    }
}
