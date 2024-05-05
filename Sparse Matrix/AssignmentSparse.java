import java.io.FileNotFoundException;
import java.io.IOException;

public class AssignmentSparse {
    public static void main(String[] args) {
        try {
            SparseMatrix matrixA = new SparseMatrix(0, 0);
            matrixA.read("A_test_1_small.txt");

            SparseMatrix matrixB = new SparseMatrix(0, 0);
            matrixB.read("B_test_1_small.txt");

            SparseMatrix matrixC = new SparseMatrix(0, 0);
            matrixC.read("C_test_1_small.txt");

            // Perform matrix addition
            SparseMatrix resultAddition = matrixA.add(matrixB);
            System.out.println("Addition result saved in text file.");
            resultAddition.write("resultAddition.txt");

            // Perform matrix multiplication
            SparseMatrix resultMultiplication = matrixA.mult(matrixC);
            System.out.println("Multiplication result saved in text file.");
            resultMultiplication.write("resultMultiplication.txt");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An I/O error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
