import java.io.*;
import java.util.Scanner;

/**
 * PROJECT: File Handling Utility
 * TASK 1: Read, Write, and Modify Text Files in Java
 * DELIVERABLE: A script demonstrating core I/O operations.
 */
public class FileUtility {

    public static void main(String[] args) {
        String fileName = "Ayush_Task.txt";

        // 1. WRITE Operation
        writeFile(fileName, "Hello Ayush! This is Task 1 of Java Utility file.\n");

        // 2. READ Operation
        System.out.println("--- Reading File Content ---");
        readFile(fileName);

        // 3. MODIFY (Append) Operation
        System.out.println("\n--- Modifying File (Appending) ---");
        modifyFile(fileName, "Adding a new line: IoT and Java are a great combo! 🔥\n");

        // Final Read to verify
        System.out.println("--- Updated File Content ---");
        readFile(fileName);
    }

    // Method to Write to a file (Overwrites existing content)
    public static void writeFile(String path, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
            System.out.println("Successfully written to: " + path);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    // Method to Read from a file
    public static void readFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to Modify/Append to a file
    public static void modifyFile(String path, String newContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.append(newContent);
            System.out.println("Modification successful!");
        } catch (IOException e) {
            System.err.println("Error modifying file: " + e.getMessage());
        }
    }
}
