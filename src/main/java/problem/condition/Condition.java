package problem.condition;

public class Condition {
    private double[] factors;
    private double limit;
    private String sign;

    public double[] getFactors() {
        return factors;
    }

    public void setFactors(double[] factors) {
        this.factors = factors;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean meetsCondition(double[] point) {
        if (point.length != factors.length) {
            return false;
        }

        double value = 0;
        for (int i = 0; i < point.length; i++) {
            value += factors[i] * point[i];
        }

        if ("<=".equals(sign)) {
            return value <= limit;
        } else {
            return value >= limit;
        }
    }

    public double countInequalityValue(double[] point) {
        double value = 0;
        for (int i = 0; i < point.length; i++) {
            value += factors[i] * point[i];
        }
        return value;
    }
}
