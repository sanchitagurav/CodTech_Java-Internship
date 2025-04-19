import java.io.*;
import java.util.Scanner;

/**
 * FileOperationsDemo demonstrates basic file operations in Java:
 * - Writing to a file
 * - Reading from a file
 * - Modifying the file content
 */
public class FileOperationsDemo {

    static final String FILE_NAME = "example.txt";

    public static void main(String[] args) {
        writeToFile("Hello, this is the original content.\n");
        readFromFile();

        modifyFile("original", "updated");
        readFromFile(); // Reading after modification
    }

    /**
     * Writes the specified content to the file.
     * If the file does not exist, it will be created.
     */
    public static void writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(content);
            System.out.println("✅ File written successfully.");
        } catch (IOException e) {
            System.err.println("❌ Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads and prints the content of the file.
     */
    public static void readFromFile() {
        System.out.println("\n📖 Reading from file:");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("   " + line);
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading from file: " + e.getMessage());
        }
    }

    /**
     * Modifies the content of the file by replacing all occurrences
     * of a target string with a replacement string.
     */
    public static void modifyFile(String target, String replacement) {
        StringBuilder newContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                newContent.append(line.replace(target, replacement)).append("\n");
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading for modification: " + e.getMessage());
            return;
        }

        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(newContent.toString());
            System.out.println("\n🔁 File modified successfully.");
        } catch (IOException e) {
            System.err.println("❌ Error writing modified content: " + e.getMessage());
        }
    }
}
