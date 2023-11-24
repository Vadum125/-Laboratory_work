package org.example;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {

        Matrix matrix1 = new Matrix(3, 3);


        float[] array1 ={1,2,3,4,5,6,7,8,-9};
        float[] array2 ={1,2,3,5,0,5,6,7,8,9,10,11,2,2,2,2};
        float[] array4 ={1,2,3,4,5,6,7,8,9};
        float[] array3 ={1,2,3,4};

        matrix1.fillMatrix(array1);
        matrix1.printMatrix();


        ImmutableMatrix matrix2= new ImmutableMatrix(matrix1);
        System.out.println(Arrays.toString(matrix2.getMatrixSize()));
        System.out.println(matrix1.equals(matrix2));

        matrix1=Matrix.addMatrices(matrix1,new Matrix(matrix2));
        matrix1.printMatrix();

        matrix2.printMatrix();
        ImmutableMatrix matrix3=new ImmutableMatrix(ImmutableMatrix.multiplyMatrixByScalar(matrix2,3));
        matrix3.printMatrix();

        Matrix matrix1_in= new Matrix(Matrix.inverse(matrix1));
        matrix1_in.printMatrix();
        ImmutableMatrix matrix_1in = new ImmutableMatrix(matrix1_in);
        matrix_1in.printMatrix();
        Matrix.multiplyMatrices(matrix1,new Matrix(matrix_1in)).printMatrix();

        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            System.out.println("Помилка: "+e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}