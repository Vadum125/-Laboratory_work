package org.example;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public final class ImmutableMatrix{
    private final int rows;
    private final int columns;
    private final float[][] matrix;
    private boolean isMatrixSet = false;

    public ImmutableMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new float[rows][columns];
    }

    public ImmutableMatrix() {
        this.rows = 0;
        this.columns = 0;
        this.matrix = new float[0][0];
    }

    public ImmutableMatrix(ImmutableMatrix other) {
        this.rows = other.rows;
        this.columns = other.columns;
        this.matrix = new float[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(other.matrix[i], 0, this.matrix[i], 0, columns);
        }
        this.isMatrixSet = true;
    }

    public ImmutableMatrix(Matrix other) {
        this.rows = other.getRows();
        this.columns = other.getColumns();
        this.matrix = new float[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = other.getElement(i, j);
            }
        }
        this.isMatrixSet = true;
    }

    public void fillMatrix(float[] values) {
        if (isMatrixSet) {
            throw new UnsupportedOperationException("Матрицю можна встановити лише один раз.");
        }

        if (values.length != rows * columns) {
            throw new IllegalArgumentException("Довжина масиву значень не відповідає розміру матриці");
        }
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = values[index];
                index++;
            }
        }
        isMatrixSet = true;
    }

    public void matrix_Random() {
        if (isMatrixSet) {
            throw new UnsupportedOperationException("Матрицю можна встановити лише один раз.");
        }
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int randomValue = random.nextInt(11);
                matrix[i][j] = randomValue;
            }
        }
        isMatrixSet = true;
    }

    public void matrix_user() {
        if (isMatrixSet) {
            throw new UnsupportedOperationException("Матрицю можна встановити лише один раз.");
        }
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("Введіть значення для елемента [" + i + "][" + j + "]: ");
                while (!scanner.hasNextFloat()) {
                    System.out.println("Будь ласка, введіть число.");
                    scanner.next();
                }
                matrix[i][j] = scanner.nextFloat();
            }
        }
        isMatrixSet = true;
    }

    public void printMatrix(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%.2f  ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }


    public float getElement(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Недійсні індекси рядка або стовпця");
        }
        return matrix[row][column];
    }


    public float[] getRow(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Недійсний індекс рядка");
        }
        return matrix[row].clone();
    }


    public float[] getColumn(int column) {
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Недійсний індекс стовпця");
        }
        float[] columnValues = new float[rows];
        for (int i = 0; i < rows; i++) {
            columnValues[i] = matrix[i][column];
        }
        return columnValues.clone();
    }
    public int[] getMatrixSize() {
        return new int[]{rows,columns};
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableMatrix other = (ImmutableMatrix) o;
        if (rows != other.rows || columns != other.columns) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Math.abs(matrix[i][j] - other.matrix[i][j]) > 0.0001) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

    public static ImmutableMatrix addMatrices(ImmutableMatrix matrix1, ImmutableMatrix matrix2) {
        if (matrix1.rows != matrix2.rows || matrix1.columns != matrix2.columns) {
            throw new IllegalArgumentException("Неможливо додати матриці різних розмірів");
        }
        ImmutableMatrix result = new ImmutableMatrix(matrix1.rows, matrix1.columns);
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                masuv[index] = matrix1.matrix[i][j] + matrix2.matrix[i][j];
                index++;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }


    public static ImmutableMatrix multiplyMatrixByScalar(ImmutableMatrix matrix, float scalar) {
        ImmutableMatrix result = new ImmutableMatrix(matrix.rows, matrix.columns);
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;

        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                masuv[index] = matrix.matrix[i][j] * scalar;
                index++;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }


    public static ImmutableMatrix multiplyMatrices(ImmutableMatrix matrix1, ImmutableMatrix matrix2) {
        if (matrix1.columns != matrix2.rows) {
            throw new IllegalArgumentException("Неможливо перемножити ці матриці");
        }
        ImmutableMatrix result = new ImmutableMatrix(matrix1.rows, matrix2.columns);
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        float namber=0;
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix2.columns; j++) {
                for (int k = 0; k < matrix1.columns; k++) {
                    namber += matrix1.matrix[i][k] * matrix2.matrix[k][j];
                }
                masuv[index] = namber;
                index++;
                namber=0;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }

    public static ImmutableMatrix transpone_Matrix(ImmutableMatrix matrix1) {
        ImmutableMatrix result = new ImmutableMatrix(matrix1.columns, matrix1.rows);
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        for (int i = 0; i < matrix1.columns; i++) {
            for (int j = 0; j < matrix1.rows; j++) {
                masuv[index] = matrix1.matrix[j][i];
                index++;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }

    public static ImmutableMatrix vector_Matrix(float[] vector) {
        ImmutableMatrix result = new ImmutableMatrix(vector.length, vector.length);
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                if (j == i) {
                    masuv[index] = vector[i];
                } else
                    masuv[index] = 0;
                index++;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }

    public static ImmutableMatrix one_Matrix(int rows, int columns) {
        if (rows != columns) {
            throw new UnsupportedOperationException("Матриця має бути квадратна");
        }
        ImmutableMatrix result = new ImmutableMatrix(rows, columns);
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                if (j == i) {
                    masuv[index] = 1;
                } else
                    masuv[index] = 0;
                index++;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }

    public static ImmutableMatrix rows_Matrix(int columns) {
        ImmutableMatrix result = new ImmutableMatrix(1, columns);
        Random random = new Random();
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        for (int j = 0; j < result.columns; j++) {
            float randomValue = random.nextFloat() * 10;
            masuv[index]=(float) (Math.round(randomValue * 10) / 10.0);
            index++;
        }
        result.fillMatrix(masuv);
        return result;
    }

    public static ImmutableMatrix columns_Matrix(int rows) {
        ImmutableMatrix result = new ImmutableMatrix(rows, 1);
        Random random = new Random();
        float[] masuv = new float[result.rows * result.columns];
        int index = 0;
        for (int i = 0; i < result.rows; i++) {
            float randomValue = random.nextFloat() * 10;
            masuv[index]=(float) (Math.round(randomValue * 10) / 10.0);
            index++;
        }
        result.fillMatrix(masuv);
        return result;
    }

    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }

    public static ImmutableMatrix inverse(ImmutableMatrix matrix) {

        int rows = matrix.getRows();
        int columns = matrix.getColumns();
        if (rows != columns) {
            throw new UnsupportedOperationException("Матриця має бути квадратна");
        }
        float[][] tempMatrix = new float[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                tempMatrix[i][j] = matrix.getElement(i, j);
            }
        }
        float[][] identityMatrix = new float[rows][columns];
        for (int i = 0; i <rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j == i) {
                    identityMatrix[i][j] = 1;
                } else
                    identityMatrix[i][j] = 0;
            }
        }

        for (int i = 0; i < rows; i++) {
            float divider = tempMatrix[i][i];
            if (divider == 0) {
                int indicator=0;
                for (int s = 0; s < rows; s++){
                    float[] array1=matrix.getRow(s);
                    float[] array2=matrix.getColumn(s);
                    indicator = 0;
                    for (float element : array1) {
                        if (element == 0) {
                            indicator++;
                        }
                    }
                    if (indicator == rows) {
                        throw new UnsupportedOperationException("Неможливо знайти обернену матрицю");
                    }
                    indicator=0;
                    for (float element : array2) {
                        if (element == 0) {
                            indicator++;
                        }
                    }
                    if (indicator == columns) {
                        throw new UnsupportedOperationException("Неможливо знайти обернену матрицю");
                    }

                }
                int notZeroRow = -1;
                for (int l = i + 1; l < rows; l++) {
                    if (tempMatrix[l][i] != 0) {
                        notZeroRow = l;
                        break;
                    }
                }
                if(notZeroRow==-1){
                    throw new UnsupportedOperationException("Неможливо знайти обернену матрицю");
                }
                float[] temp1 = tempMatrix[i];
                tempMatrix[i] = tempMatrix[notZeroRow];
                tempMatrix[notZeroRow] = temp1;
                float[] temp2 = identityMatrix[i];
                identityMatrix[i] = identityMatrix[notZeroRow];
                identityMatrix[notZeroRow] = temp2;
                divider = tempMatrix[i][i];
            }

            for (int j = 0; j < rows; j++) {
                tempMatrix[i][j] /= divider;
                identityMatrix[i][j] /= divider;
            }
            for (int k = 0; k < rows; k++) {
                if (k != i) {
                    float factor = tempMatrix[k][i];
                    for (int j = 0; j < rows; j++) {
                        tempMatrix[k][j] -= factor * tempMatrix[i][j];
                        identityMatrix[k][j] -= factor * identityMatrix[i][j];
                    }
                }
            }
        }
        ImmutableMatrix result = new ImmutableMatrix(rows, columns);
        float[] masuv = new float[result.rows * result.columns];
        int index=0;
        for (int i = 0; i <rows; i++) {
            for (int j = 0; j < columns; j++) {
                masuv[index]=identityMatrix[i][j];
                index++;
            }
        }
        result.fillMatrix(masuv);
        return result;
    }
}
