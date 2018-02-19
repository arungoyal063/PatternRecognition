package utils;

import Jama.Matrix;
import functions.Func;

/**
 * @author Ondrej Kratochvil
 */
public class MatrixUtils {


    public static Matrix newDiag(double scalar, int width) {
        double[][] doubles = new double[width][width];

        for (int i = 0, j = 0; i < width; ++i, ++j)
            doubles[i][j] = scalar;

        return new Matrix(doubles);
    }

    /**
     * Element-by-element matrix modification.
     */
    public static void modifyElementByElement(Matrix matrix, Func func) {
        double[][] doubles = matrix.getArray();

        int rows = matrix.getRowDimension();
        int cols = matrix.getColumnDimension();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j)
                doubles[i][j] = func.compute(doubles[i][j]);
        }
    }

    /**
     * Element-by-element matrix modification.
     */
    public static void modifyDiagonal(Matrix matrix, Func func) {
        double[][] doubles = matrix.getArray();

        int rows = matrix.getRowDimension();

        for (int i = 0, j = 0; i < rows; ++i, ++j)
            doubles[i][j] = func.compute(doubles[i][j]);
    }
}
