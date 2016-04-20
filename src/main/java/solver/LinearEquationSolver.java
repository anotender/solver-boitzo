package solver;

public class LinearEquationSolver implements Solver {

    private final double[][] a;
    private final double[] b;

    public LinearEquationSolver(double[][] a, double[] b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double[] solve() {
        double mainDeterminant = countDeterminant(a);

        if (mainDeterminant != 0) {
            double firstVariableDeterminant = countDeterminant(new double[][]{
                    {b[0], a[0][1]},
                    {b[1], a[1][1]}
            });
            double secondVariableDeterminant = countDeterminant(new double[][]{
                    {a[0][0], b[0]},
                    {a[1][0], b[1]}
            });

            return new double[]{
                    firstVariableDeterminant / mainDeterminant,
                    secondVariableDeterminant / mainDeterminant
            };
        }

        return null;
    }

    private double countDeterminant(double[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }
}
