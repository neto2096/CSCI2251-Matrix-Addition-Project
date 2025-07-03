
/*
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

}