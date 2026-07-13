// Tanner Elston 7/8/26 CSD420

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test program that verifies WordProcessor reads words correctly and
 * produces properly sorted, non-duplicate ascending/descending lists.
 */
public class TestWordProcessor {

    /**
     * Runs three checks against WordProcessor's output:
     * 1) the unique word count matches the file's distinct word count,
     * 2) the ascending list is actually sorted ascending, and
     * 3) the descending list is actually sorted descending.
     * Results are printed to standard output as each test runs.
     *
     * @throws IOException if collection_of_words.txt cannot be read
     */
    public static void main(String[] args) throws IOException {
        int testsPassed = 0;
        int testsFailed = 0;

        final String filename = "collection_of_words.txt";

        Set<String> uniqueWords = WordProcessor.readUniqueWords(filename);
        List<String> ascending = WordProcessor.ascendingOrder(uniqueWords);
        List<String> descending = WordProcessor.descendingOrder(uniqueWords);

        System.out.println("Running tests on WordProcessor...");
        System.out.println();

        /*
         * Test 1: The number of unique words returned should match the
         * number of distinct (lower-cased) words actually in the file.
         */
        List<String> allWordsFromFile = new ArrayList<>();
        /*
         * try-with-resources automatically closes the BufferedReader/FileReader
         * even if an exception is thrown, so no explicit finally block is needed.
         * Source: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
         */
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String token : line.trim().split("\\s+")) {
                    if (!token.isEmpty()) {
                        allWordsFromFile.add(token.toLowerCase());
                    }
                }
            }
        }
        /*
         * HashSet is used here to strip duplicates for comparison;
         * ordering doesn't matter for this check, only distinct count does.
         * Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashSet.html
         */
        Set<String> expectedUnique = new HashSet<>(allWordsFromFile);

        if (expectedUnique.size() == uniqueWords.size()) {
            System.out.println("PASSED: Test 1 - unique word count is correct ("
                    + uniqueWords.size() + " words)");
            testsPassed++;
        } else {
            System.out.println("FAILED: Test 1 - expected " + expectedUnique.size()
                    + " unique words but got " + uniqueWords.size());
            testsFailed++;
        }

        /*
         * Test 2: ascending list must actually be sorted ascending.
         * String.compareTo() returns a negative number when the calling string
         * lexicographically precedes the argument, which is used here to check order.
         * Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html
         */
        boolean isAscending = true;
        for (int i = 0; i < ascending.size() - 1; i++) {
            if (ascending.get(i).compareTo(ascending.get(i + 1)) > 0) {
                isAscending = false;
                break;
            }
        }
        if (isAscending) {
            System.out.println("PASSED: Test 2 - words are in ascending order");
            testsPassed++;
        } else {
            System.out.println("FAILED: Test 2 - words are NOT in ascending order");
            testsFailed++;
        }

        /*
         * Test 3: descending list must actually be sorted descending.
         */
        boolean isDescending = true;
        for (int i = 0; i < descending.size() - 1; i++) {
            if (descending.get(i).compareTo(descending.get(i + 1)) < 0) {
                isDescending = false;
                break;
            }
        }
        if (isDescending) {
            System.out.println("PASSED: Test 3 - words are in descending order");
            testsPassed++;
        } else {
            System.out.println("FAILED: Test 3 - words are NOT in descending order");
            testsFailed++;
        }

        System.out.println();
        System.out.println("Ascending order result:  " + ascending);
        System.out.println("Descending order result: " + descending);

        System.out.println();
        System.out.println("Total tests passed: " + testsPassed);
        System.out.println("Total tests failed: " + testsFailed);
    }
}