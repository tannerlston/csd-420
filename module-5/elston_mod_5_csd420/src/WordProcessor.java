// Tanner Elston 7/8/26 CSD420

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Reads words from a text file and provides the non-duplicate words
 * in both ascending and descending order.
 */
public class WordProcessor {

    /**
     * Reads every word from the given file and returns the set of
     * unique (non-duplicate) words. Words are lower-cased before being
     * added so that, for example, "Apple" and "apple" are treated as duplicates.
     *
     * @param filename relative path to the text file to read
     * @return a Set containing each distinct word exactly once
     * @throws IOException if the file cannot be read
     */
    public static Set<String> readUniqueWords(String filename) throws IOException {
        // TreeSet automatically discards duplicates and keeps elements sorted.
        // Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html
        Set<String> uniqueWords = new TreeSet<>();

         /*
          * try-with-resources automatically closes the BufferedReader/FileReader
          * even if an exception is thrown, so no explicit finally block is needed.
          * Source: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
         */

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        uniqueWords.add(token.toLowerCase());
                    }
                }
            }
        }

        return uniqueWords;
    }

    /*
     * Returns the given words sorted in ascending (A-Z) order.
     * Collections.sort() with no comparator sorts using natural (ascending) order.
     * Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html
     */
    public static List<String> ascendingOrder(Set<String> words) {
        List<String> list = new ArrayList<>(words);
        Collections.sort(list);
        return list;
    }

    /*
     * Returns the given words sorted in descending (Z-A) order.
     * Collections.reverseOrder() returns a Comparator that sorts in the reverse of natural ordering (descending, Z-A).
     * Source: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html
     */
    public static List<String> descendingOrder(Set<String> words) {
        List<String> list = new ArrayList<>(words);
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    public static void main(String[] args) throws IOException {
        // Relative pathing for the text file containing word assortment (fruits and vegetables)
        final String filename = "collection_of_words.txt";

        Set<String> uniqueWords = readUniqueWords(filename);

        List<String> ascending = ascendingOrder(uniqueWords);
        List<String> descending = descendingOrder(uniqueWords);

        System.out.println("Non-duplicate words in ASCENDING order:");
        System.out.println(String.join(", ", ascending));

        System.out.println();
        System.out.println("Non-duplicate words in DESCENDING order:");
        System.out.println(String.join(", ", descending));
    }
}