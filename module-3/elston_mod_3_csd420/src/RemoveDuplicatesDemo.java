// Tanner Elston, CSD402, 6/26/26

import java.util.ArrayList;
import java.util.Random;

public class RemoveDuplicatesDemo {

    /**
     * Generic static method that takes an ArrayList of any type E
     * and returns a NEW ArrayList containing the same elements but
     * with no duplicates. The original list is left unchanged.
     * Source: https://docs.oracle.com/javase/tutorial/java/generics/inheritance.html
     * Source: https://docs.oracle.com/javase/tutorial/java/generics/methods.html
     */
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        ArrayList<E> result = new ArrayList<>();

        for (E item : list) {
            // Only add the item if it isn't already in the result list
            if (!result.contains(item)) {
                result.add(item);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ArrayList<Integer> original = new ArrayList<>();
        Random rand = new Random();

        // Fill the original ArrayList with 50 random values from 1 to 20
        for (int i = 0; i < 50; i++) {
            original.add(rand.nextInt(20) + 1); // nextInt(20) -> 0-19, +1 -> 1-20
        }

        ArrayList<Integer> noDuplicates = removeDuplicates(original);

        System.out.println("Original ArrayList (" + original.size() + " values):");
        System.out.println(original);

        System.out.println();

        System.out.println("ArrayList with duplicates removed (" + noDuplicates.size() + " values):");
        System.out.println(noDuplicates);
    }
}