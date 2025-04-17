package com.yaksha.assignment;

import java.io.File;
import java.io.IOException;

public class TryCatchBlock {

	public static void main(String[] args) {
		// **Simulate an operation that could throw an exception (like division by
		// zero)**
		try {
			int result = 10 / 0; // This will throw ArithmeticException
		} catch (ArithmeticException e) {
			// **Handle the exception**
			System.out.println("Exception caught: " + e.getMessage());
		}

		// **Another try-catch block for handling a file operation (simulated)**
		try {
			String path = "non_existent_file.txt";
			File file = new File(path);
			if (!file.exists()) {
				throw new IOException("File not found: " + path);
			}
		} catch (IOException e) {
			// **Handle the exception**
			System.out.println("Exception caught: " + e.getMessage());
		}
	}
}
