package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.TryStmt;

public class AutoGrader {

	// Test if the code correctly implements try-catch blocks
	public boolean testTryCatchBlock(String filePath) throws IOException {
		System.out.println("Starting testTryCatchBlock with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		// Flags to check presence and correct usage of try-catch blocks
		boolean hasMainMethod = false;
		boolean hasTryCatchBlock = false;
		boolean handledArithmeticException = false;
		boolean handledIOException = false;

		// Check for method declarations
		for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
			String methodName = method.getNameAsString();
			// Check for the presence of the main method
			if (methodName.equals("main")) {
				hasMainMethod = true;
			}
		}

		// Check for presence of try-catch blocks
		for (TryStmt tryStmt : cu.findAll(TryStmt.class)) {
			hasTryCatchBlock = true;
			if (tryStmt.getCatchClauses().stream()
					.anyMatch(c -> c.getParameter().getType().asString().equals("ArithmeticException"))) {
				handledArithmeticException = true;
			}
			if (tryStmt.getCatchClauses().stream()
					.anyMatch(c -> c.getParameter().getType().asString().equals("IOException"))) {
				handledIOException = true;
			}
		}

		// Log method presence and try-catch usage
		System.out.println("Method 'main' is " + (hasMainMethod ? "present" : "NOT present"));
		System.out.println("Try-catch block is " + (hasTryCatchBlock ? "present" : "NOT present"));
		System.out.println("ArithmeticException is " + (handledArithmeticException ? "handled" : "NOT handled"));
		System.out.println("IOException is " + (handledIOException ? "handled" : "NOT handled"));

		// Final result
		boolean result = hasMainMethod && hasTryCatchBlock && handledArithmeticException && handledIOException;

		System.out.println("Test result: " + result);
		return result;
	}
}
