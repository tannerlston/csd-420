// Tanner Elston, CSD420, 6/19/26

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Generates an array of 5 random integers and an array of 5 random doubles,
 * then writes (appends) them to a binary data file.
 *
 * If the file does not exist, it is created.
 * If the file already exists, the new data is appended to the end of it
 * (this is why FileOutputStream is opened with "true" for append mode).
 */
public class DataFileWriter {

    private static final String FILE_NAME = "tanner_datafile.dat";

    public static void main(String[] args) {

        int[] randomInts = new int[5];
        double[] randomDoubles = new double[5];

        Random rand = new Random();

        // Fill the integer array with random values between 1 and 100
        for (int i = 0; i < randomInts.length; i++) {
            randomInts[i] = rand.nextInt(100) + 1;
        }

        // Fill the double array with random values between 0.0 and 100.0
        for (int i = 0; i < randomDoubles.length; i++) {
            randomDoubles[i] = rand.nextDouble() * 100;
        }

        // Open the file in APPEND mode (the "true" argument).
        // FileOutputStream creates the file automatically if it doesn't exist yet.
        // Source: https://www.tutorialspoint.com/java/java_write_file.htm

        try (DataOutputStream outFile =
                     new DataOutputStream(new FileOutputStream(FILE_NAME, true))) {

            // Write the 5 integers
            for (int value : randomInts) {
                outFile.writeInt(value);
            }

            // Write the 5 doubles
            for (double value : randomDoubles) {
                outFile.writeDouble(value);
            }

            System.out.println("Data successfully written/appended to \"" + FILE_NAME + "\"");

            System.out.println("\nIntegers written:");
            for (int value : randomInts) {
                System.out.print(value + "   ");
            }

            System.out.println("\n\nDoubles written:");
            for (double value : randomDoubles) {
                System.out.printf("%.2f   ", value);
            }
            System.out.println();

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}