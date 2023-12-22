package org.example;
import java.util.Random;
import java.util.Scanner;
import java.util.Objects;
import java.util.Arrays;


public class Matrix {
    private int rows;
    private int columns;
    private float[][] matrix;

    //конструктор створення матриці заданого розміру
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new float[rows][columns];
    }

    //конструктор створення пустої матриці
    public Matrix() {
        this.rows = 0;
        this.columns = 0;
        this.matrix = new float[rows][columns];
    }

    // Конструктор для копіювання матриці
    public Matrix(Matrix other) {
        this.rows = other.rows;
        this.columns = other.columns;
        this.matrix = new float[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = other.matrix[i][j];
            }
        }
    }

    public Matrix(ImmutableMatrix other) {
        this.rows = other.getRows();
        this.columns = other.getColumns();
        this.matrix = new float[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = other.getElement(i, j);
            }
        }
    }
    //Заповнення матриці масивом чисел
    public void fillMatrix(float[] values) {
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
    }

    //Метод для заповнення матриці випадковими числами
    public void matrix_Random() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int randomValue = random.nextInt(21) - 10;
                //float randomValue = random.nextFloat() * 20 - 10;
                matrix[i][j] = randomValue;
            }
        }
    }

    //Метод для заповнення числами від коритувача
    public void matrix_user() {
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
    }

    // Метод для виведення матриці на екран
    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%.2f  ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Метод для отримання значення конкретного елемента
    public float getElement(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Недійсні індекси рядка або стовпця");
        }
        return matrix[row][column];
    }

    /// Метод для отримання заданого рядка
    public float[] getRow(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Недійсний індекс рядка");
        }
        float[] rowValues = new float[columns];
        for (int i = 0; i < columns; i++) {
            rowValues[i] = matrix[row][i];
        }
        return rowValues;
    }

    // Метод для отримання заданого стовпця
    public float[] getColumn(int column) {
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Недійсний індекс стовпця");
        }
        float[] columnValues = new float[rows];
        for (int i = 0; i < rows; i++) {
            columnValues[i] = matrix[i][column];
        }
        return columnValues;
    }

    // Метод для отримання розмірності матриці
    public int[] getMatrixSize() {
        return new int[]{rows,columns};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix other = (Matrix) o;
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

    // Метод для додавання двох матриць
    public static Matrix addMatrices(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows != matrix2.rows || matrix1.columns != matrix2.columns) {
            throw new IllegalArgumentException("Неможливо додати матриці різних розмірів");
        }
        Matrix result = new Matrix(matrix1.rows, matrix1.columns);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                result.matrix[i][j] = matrix1.matrix[i][j] + matrix2.matrix[i][j];
            }
        }
        return result;
    }

    // Метод для множення матриці на скаляр
    public static Matrix multiplyMatrixByScalar(Matrix matrix, float scalar) {
        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                result.matrix[i][j] = matrix.matrix[i][j] * scalar;
            }
        }
        return result;
    }
    // Метод для множення матриць
    public static Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2) {
        if (matrix1.columns != matrix2.rows) {
            throw new IllegalArgumentException("Неможливо перемножити ці матриці");
        }
        Matrix result = new Matrix(matrix1.rows, matrix2.columns);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix2.columns; j++) {
                for (int k = 0; k < matrix1.columns; k++) {
                    result.matrix[i][j] += matrix1.matrix[i][k] * matrix2.matrix[k][j];
                }
            }
        }
        return result;
    }

    // Метод для транспонування  матриці
    public static Matrix transpone_Matrix(Matrix matrix1) {
        Matrix result = new Matrix(matrix1.columns, matrix1.rows);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.columns; j++) {
                result.matrix[j][i] = matrix1.matrix[i][j];
            }
        }
        return result;
    }
    // Метод повернення діагональної матриці на основі заданого вектора
    public static Matrix vector_Matrix(float[] vector) {
        Matrix result = new Matrix(vector.length, vector.length);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                if (j == i) {
                    result.matrix[i][j] = vector[i];
                } else
                    result.matrix[i][j] = 0;
                }
            }
        return result;
    }

    //Метод повернення одиничної матриці
    public static Matrix one_Matrix(int rows, int columns) {
        if (rows != columns) {
            throw new UnsupportedOperationException("Матриця має бути квадратна");
        }
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                if (j == i) {
                    result.matrix[i][j] = 1;
                } else
                    result.matrix[i][j] = 0;
            }
        }
        return result;
    }
    //Метод для встановлення значення елемента в методах повернення матриці строки і матриці стовпця
    public void setElement(int row, int column, float value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IllegalArgumentException("Неправильні індекси рядка або стовпця");
        }
        matrix[row][column] = value;
    }

    //Метод повернення матриці строки
    public static Matrix rows_Matrix(int columns) {
        Matrix result = new Matrix(1, columns);
        Random random = new Random();
        for (int j = 0; j < result.columns; j++) {
            float randomValue = random.nextFloat() * 10;
            result.setElement(0, j, (float) (Math.round(randomValue * 10) / 10.0));
        }
        return result;
    }
    //Метод повернення матриці стовпця
    public static Matrix columns_Matrix(int rows) {
        Matrix result = new Matrix(rows, 1);
        Random random = new Random();
        for (int i = 0; i < result.rows; i++) {
            float randomValue = random.nextFloat() * 10;
            result.setElement(i, 0, (float) (Math.round(randomValue * 10) / 10.0));

        }
        return result;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }

    public void swapRows(int row1, int row2) {
        float[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;

    }
    public static Matrix inverse(Matrix matrix) {
        int rows = matrix.getRows();
        int columns = matrix.getColumns();
        if (rows != columns) {
            throw new UnsupportedOperationException("Матриця має бути квадратна");
        }
        Matrix tempMatrix = new Matrix(matrix);
        Matrix identityMatrix =  Matrix.one_Matrix(rows, columns);

        for (int i = 0; i < rows; i++) {
            float divider = tempMatrix.matrix[i][i];
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
                    if (tempMatrix.matrix[l][i] != 0) {
                        notZeroRow = l;
                        break;
                    }
                }
                if(notZeroRow==-1){
                    throw new UnsupportedOperationException("Неможливо знайти обернену матрицю");
                }
                tempMatrix.swapRows(i, notZeroRow);
                identityMatrix.swapRows(i, notZeroRow);
                divider = tempMatrix.matrix[i][i];
            }
            for (int j = 0; j < rows; j++) {
                tempMatrix.matrix[i][j] /= divider;
                identityMatrix.matrix[i][j] /= divider;
            }
            for (int k = 0; k < rows; k++) {
                if (k != i) {
                    float factor = tempMatrix.matrix[k][i];
                    for (int j = 0; j < rows; j++) {
                        tempMatrix.matrix[k][j] -= factor * tempMatrix.matrix[i][j];
                        identityMatrix.matrix[k][j] -= factor * identityMatrix.matrix[i][j];
                    }
                }
            }
        }
        return identityMatrix;
    }

}
