// Tanner Elston 7/16/26, CSD420

import java.util.Arrays;
import java.util.Comparator;

/**
 * BubbleSortTest
 *
 * This class contains test code that exercises both generic bubble sort
 * methods in BubbleSort.java:
 *
 *   - sort(T[] array)                     using Comparable
 *   - sort(T[] array, Comparator<T> comp) using Comparator
 *
 * Each test prints the array before and after sorting, along with a
 * pass/fail result determined by checking that the array is in the
 * expected order after the sort runs. A simple counter tracks how many
 * tests pass out of the total, and a summary is printed at the end.
 *
 * A practical inventory-management scenario (see Product.java) is used
 * to demonstrate both interfaces on the same data: products are sorted
 * alphabetically by name using Comparable (their natural ordering), and
 * separately sorted by price using a Comparator. The example mirrors a real
 * shopping site letting a user switch between "Name" and "Price:
 * Low to High" sort options. Two additional cases (a reverse-sorted
 * array and an array with duplicate values) confirm the swap logic
 * itself works correctly, and a null-array case confirms the methods
 * fail safely instead of throwing an exception.
 */

public class BubbleSortTest {

    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {

        testSortProductsByNameComparable();
        testSortProductsByPriceComparator();
        testReverseSortedArray();
        testArrayWithDuplicates();
        testNullArray();

        System.out.println("\n=====================================");
        System.out.println("Tests passed: " + testsPassed + " / " + testsTotal);
        System.out.println("=====================================");
    }

    // ---------------------------------------------------------------
    // Comparable test
    // ---------------------------------------------------------------

    private static void testSortProductsByNameComparable() {
        Product[] inventory = {
                new Product("Wireless Mouse", 24.99, 50),
                new Product("Bluetooth Speaker", 59.99, 12),
                new Product("USB-C Cable", 9.99, 200)
        };
        System.out.println("Test: Sort Products by name (Comparable)");
        System.out.println("  Before: " + Arrays.toString(inventory));

        BubbleSort.sort(inventory);

        System.out.println("  After:  " + Arrays.toString(inventory));
        check(isSortedAscending(inventory));
    }

    // ---------------------------------------------------------------
    // Comparator test
    // ---------------------------------------------------------------

    private static void testSortProductsByPriceComparator() {
        Product[] inventory = {
                new Product("Wireless Mouse", 24.99, 50),
                new Product("Bluetooth Speaker", 59.99, 12),
                new Product("USB-C Cable", 9.99, 200)
        };
        System.out.println("Test: Sort Products by price, low to high (Comparator)");
        System.out.println("  Before: " + Arrays.toString(inventory));

        // A shopping site's "Price: Low to High" filter would use a
        // Comparator like this instead of changing Product's natural
        // (name-based) ordering.
        Comparator<Product> byPriceAscending = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        };

        BubbleSort.sort(inventory, byPriceAscending);

        System.out.println("  After:  " + Arrays.toString(inventory));
        boolean sorted = true;
        for (int i = 0; i < inventory.length - 1; i++) {
            if (inventory[i].getPrice() > inventory[i + 1].getPrice()) {
                sorted = false;
                break;
            }
        }
        check(sorted);
    }

    // ---------------------------------------------------------------
    // Edge case tests
    // ---------------------------------------------------------------

    private static void testReverseSortedArray() {
        Integer[] numbers = {9, 7, 5, 3, 1};
        System.out.println("Test: Reverse-sorted array (worst case)");
        System.out.println("  Before: " + Arrays.toString(numbers));

        BubbleSort.sort(numbers);

        System.out.println("  After:  " + Arrays.toString(numbers));
        check(isSortedAscending(numbers));
    }

    private static void testArrayWithDuplicates() {
        Integer[] numbers = {4, 2, 4, 1, 2, 4};
        System.out.println("Test: Array with duplicate values");
        System.out.println("  Before: " + Arrays.toString(numbers));

        BubbleSort.sort(numbers);

        System.out.println("  After:  " + Arrays.toString(numbers));
        check(isSortedAscending(numbers));
    }

    private static void testNullArray() {
        Integer[] numbers = null;
        System.out.println("Test: Null array (should not throw an exception)");

        boolean threwException = false;
        try {
            BubbleSort.sort(numbers);
        } catch (Exception e) {
            threwException = true;
        }

        System.out.println("  Result: " + (threwException ? "threw exception" : "handled safely"));
        check(!threwException);
    }

    // ---------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------

    /**
     * Checks whether an array of Comparable elements is sorted in
     * ascending order.
     */
    private static <T extends Comparable<T>> boolean isSortedAscending(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Records the result of a test and prints PASS or FAIL.
     */
    private static void check(boolean condition) {
        testsTotal++;
        if (condition) {
            testsPassed++;
            System.out.println("  Result: PASS\n");
        } else {
            System.out.println("  Result: FAIL\n");
        }
    }
}