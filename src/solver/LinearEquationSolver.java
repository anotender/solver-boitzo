package solver;


import exceptions.WrongMatrixSizeException;

public class LinearEquationSolver implements Solver {

    private final int[][] a;
    private final int[] b;

    public LinearEquationSolver(int[][] a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double[] solve() {
        int mainDeterminant = countDeterminant(a);

        if (mainDeterminant != 0) {
            int firstVariableDeterminant = countDeterminant(new int[][]{
                    {b[0], a[0][1]},
                    {b[1], a[1][1]}
            });
            int secondVariableDeterminant = countDeterminant(new int[][]{
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

    private int countDeterminant(int[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }
}
