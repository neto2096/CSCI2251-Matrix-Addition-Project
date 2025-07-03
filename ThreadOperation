public class ThreadOperation extends Thread {
    private final int[][] A;
    private final int[][] B;
    private final int[][] C;
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;
    private final String quadrant;

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