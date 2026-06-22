// Tanner Elston, CSD420, 6/18/26

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Reads the binary data file produced by DataFileWriter and displays its
 * contents. Each time DataFileWriter runs, it appends one "batch" of
 * 5 ints followed by 5 doubles. This program keeps reading batches until
 * it reaches the end of the file, so it will display every batch that
 * has ever been appended.
 *
 * Source: https://www.tutorialspoint.com/java/java_read_file.htm
 */
public class DataFileReader {

    // Must match the file name used in DataFileWriter
    private static final String FILE_NAME = "tanner_datafile.dat";

    public static void main(String[] args) {

        int runNumber = 1;


        try (DataInputStream inFile = new DataInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Reading data from \"" + FILE_NAME + "\"\n");

            while (true) {
                int[] ints = new int[5];
                double[] doubles = new double[5];

                // Read 5 integers
                for (int i = 0; i < 5; i++) {
                    ints[i] = inFile.readInt();
                }

                // Read 5 doubles
                for (int i = 0; i < 5; i++) {
                    doubles[i] = inFile.readDouble();
                }

                // Display this batch
                System.out.println("Run " + runNumber + ":");

                System.out.print("  Integers: ");
                for (int value : ints) {
                    System.out.print(value + "   ");
                }

                System.out.print("\n  Doubles:  ");
                for (double value : doubles) {
                    System.out.printf("%.2f   ", value);
                }
                System.out.println("\n");

                runNumber++;
            }

        } catch (EOFException eof) {
            // Expected: this is how we know we've reached the end of the file
            if (runNumber == 1) {
                System.out.println("No data found in file.");
            } else {
                System.out.println("End of file reached. Displayed "
                        + (runNumber - 1) + " run(s) of data.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage()
                    + "\nMake sure DataFileWriter has been run at least once first.");
        }
    }
}