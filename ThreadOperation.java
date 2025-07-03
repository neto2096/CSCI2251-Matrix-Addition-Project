/*
Name: Ernesto Morales Carrasco
Email: emoralescarras@cnm.edu
Assignment: Matrix Addition Part 1
Purpose: Extends Thread to perform addition of two submatrices (from matrices A and B) 
in a specified quadrant, storing the result in a shared matrix C, enabling concurrent processing for efficiency.
*/

public class ThreadOperation extends Thread {
    private final int[][] A;
    private final int[][] B;
    private final int[][] C;
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;
    private final String quadrant;


    /**
     * Constructs a ThreadOperation to add submatrices of A and B within a specified quadrant,
     * storing the result in the shared matrix C.
     *
     * @param A The first input matrix
     * @param B The second input matrix
     * @param C The result matrix to store the sum
     * @param startRow Starting row index of the quadrant
     * @param startCol Starting column index of the quadrant
     * @param endRow Ending row index of the quadrant (exclusive)
     * @param endCol Ending column index of the quadrant (exclusive)
     * @param quadrant String identifier for the quadrant (e.g., "00" for upper-left)
     */
    public ThreadOperation(int[][] A, int[][] B, int[][] C, int startRow, int startCol, int endRow, int endCol, String quadrant) {
        this.A = A;
        this.B = B;
        this.C = C; // Shared result matrix
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.quadrant = quadrant;
    }

     /**
     * Executes the thread, performing element-wise addition of submatrices A and B
     * within the assigned quadrant and storing the result in C. Includes boundary checks
     * to prevent out-of-bounds access.
     */
    @Override
    public void run() {
        // Perform addition for the assigned submatrix
        for (int i = startRow; i < endRow && i < A.length; i++) {
            for (int j = startCol; j < endCol && j < A[0].length; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
    }
}