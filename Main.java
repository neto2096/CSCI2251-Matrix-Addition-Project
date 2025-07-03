
/*
Name: Ernesto Morales Carrasco
Email: emoralescarras@cnm.edu
Assignment: Matrix Addition Part 1
Purpose: Reads two matrices from a file, splits them into four submatrices, 
creates threads to perform addition on each submatrix pair, and prints the resulting matrix.


This code is provided to give you a
starting place. It should be modified.
No further imports are needed.
To earn full credit, you must also
answer the following question:

Q1: One of the goals of multi-threading
is to minimize the resource usage, such
as memory and processor cycles. In three
sentences, explain how multi-threaded
code accomplishes this goal. Consider
writing about blocking on I/O, multicore 
machines, how sluggish humans are,
threads compared to processes, etcetera,
and connect these issues to 
multi-threading.

Multi-threading minimizes resource usage by allowing concurrent 
execution on multicore machines, efficiently utilizing processor 
cycles as threads share the same memory space, unlike processes 
which require separate memory allocations. By splitting tasks, 
threads can process independent data simultaneously, reducing 
time during I/O operations. Compared to processes, threads are 
lightweight, reducing memory and optimizing performance for
 computationally intensive tasks.

*/
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please provide a matrix file as a command-line argument.");
			return;
		}

		try (Scanner fileReader = new Scanner(new File(args[0]))) {
			// Read dimensions
			int rows = fileReader.nextInt();
			int cols = fileReader.nextInt();

			// Read matrices A and B
			int[][] A = matrixFromFile(rows, cols, fileReader);
			int[][] B = matrixFromFile(rows, cols, fileReader);

			// Create result matrix C
			int[][] C = new int[rows][cols];

			// Calculate submatrix boundaries
			int midRow = (rows + 1) / 2; // Ceiling to handle odd rows
			int midCol = (cols + 1) / 2; // Ceiling to handle odd cols

			// Create and start threads for each quadrant
			ThreadOperation[] threads = new ThreadOperation[4];
			threads[0] = new ThreadOperation(A, B, C, 0, 0, midRow, midCol, "00");
			threads[1] = new ThreadOperation(A, B, C, 0, midCol, midRow, cols, "01");
			threads[2] = new ThreadOperation(A, B, C, midRow, 0, rows, midCol, "10");
			threads[3] = new ThreadOperation(A, B, C, midRow, midCol, rows, cols, "11");

			for (ThreadOperation thread : threads) {
				thread.start();
			}

			// Join threads to ensure completion
			for (ThreadOperation thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					System.out.println("Thread interrupted: " + e.getMessage());
				}
			}

			// Print result matrix
			print2dArray(C);

			// Test print2dArray with a sample array
			int[][] testArray = {
					{ 1, 2, 3 },
					{ 4, 5, 6 },
					{ 7, 8, 9 }
			};
			System.out.println("\nTest 2D Array:");
			print2dArray(testArray);

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	/**
     * Reads a matrix of specified dimensions from a file using a Scanner.
     * Expects rows * cols integers in row-major order.
     *
     * @param rows Number of rows in the matrix
     * @param cols Number of columns in the matrix
     * @param fileReader Scanner object reading from the input file
     * @return A 2D integer array representing the matrix
     * @throws IllegalArgumentException if the file lacks sufficient data or contains invalid input
     */
	public static int[][] matrixFromFile(int rows, int cols, Scanner fileReader) {
		int[][] matrix = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = fileReader.nextInt();
			}
		}
		return matrix;
	}

	/**
     * Prints a 2D integer array to the console with aligned columns using printf.
     * Each element is formatted to occupy 4 spaces for consistent alignment.
     *
     * @param matrix The 2D integer array to print
     */
	public static void print2dArray(int[][] matrix) {
		for (int[] row : matrix) {
			for (int value : row) {
				System.out.printf("%4d", value);
			}
			System.out.println();
		}
	}
}
