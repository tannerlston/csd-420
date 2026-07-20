// Tanner Elston 7/14/26, CSD420

import java.util.Comparator;

/**
 * BubbleSort
 *
 * This class provides two generic bubble sort methods for sorting arrays
 * of objects:
 *
 *   1. sort(T[] array)                     - sorts using the Comparable
 *                                             interface (the natural
 *                                             ordering of the elements).
 *   2. sort(T[] array, Comparator<T> comp) - sorts using a Comparator
 *                                             object, which allows the
 *                                             caller to define a custom
 *                                             ordering without changing
 *                                             the class of the elements
 *                                             being sorted.
 *
 * Bubble sort works by repeatedly stepping through the array, comparing
 * each pair of adjacent elements, and swapping them if they are in the
 * wrong order. This process repeats until a full pass is made with no
 * swaps, which means the array is sorted. A "swapped" flag is used so
 * that the algorithm can exit early once the array becomes sorted,
 * instead of always running the full n-1 passes.
 *
 * Both methods run in O(n^2) time in the worst and average case, and
 * O(n) time in the best case (an already-sorted array), because of the
 * early-exit optimization.
 *
 * SOURCES:
 *
 *   The core sorting concept repeatedly compares adjacent
 *   elements and swapping out-of-order pairs until a full pass produces
 *   no swaps. The following source formally defines the
 *   algorithm and illustrates how a single pass "bubbles"
 *   the largest unsorted element toward the end of the array, and how
 *   repeated passes progressively produce a fully sorted array:
 *
 *     https://mitpress.ublish.com/book/introduction-algorithms
 *    (Introduction to Algorithms, 3rd ed., Problem 2-2: bubblesort)
 *
 *   Background reading on the interfaces used in this class:
 *
 *     Oracle. (n.d.). Comparable<T> [Interface documentation].
 *     Java SE 17 & JDK 17 API Documentation.
 *     https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Comparable.html
 *
 *     Oracle. (n.d.). Comparator<T> [Interface documentation].
 *     Java SE 17 & JDK 17 API Documentation.
 *     https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html
 */
public class BubbleSort {

    /**
     * Sorts the given array in ascending order using each element's
     * natural ordering, as defined by the Comparable interface.
     *
     * @param <T>   the type of elements in the array; must implement
     *              Comparable so that elements can be compared to one
     *              another
     * @param array the array of elements to sort (sorted in place)
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        // Guard against null or trivially small arrays; nothing to sort.
        if (array == null || array.length < 2) {
            return;
        }

        int n = array.length;
        boolean swapped;

        // Each pass "bubbles" the largest remaining unsorted element up
        // to its correct position at the end of the array.
        for (int pass = 0; pass < n - 1; pass++) {
            swapped = false;

            // After each pass, the last "pass" elements are already in
            // their final sorted position, so we don't need to check them.
            for (int i = 0; i < n - 1 - pass; i++) {
                // compareTo() > 0 means array[i] is "greater than"
                // array[i + 1] according to natural ordering, so they
                // are out of order and need to be swapped.
                if (array[i].compareTo(array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }

            // If no elements were swapped during this pass, the array
            // is already fully sorted, so we can stop early.
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Sorts the given array according to the ordering imposed by the
     * supplied Comparator. This allows sorting objects that do not
     * implement Comparable, or sorting Comparable objects by some order
     * other than their natural ordering (for example, sorting Strings
     * by length instead of alphabetically).
     *
     * @param <T>        the type of elements in the array
     * @param array      the array of elements to sort (sorted in place)
     * @param comparator the Comparator used to determine element order
     */
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        // Guard against null input; nothing meaningful to do without
        // an array or a comparator to define the ordering.
        if (array == null || array.length < 2 || comparator == null) {
            return;
        }

        int n = array.length;
        boolean swapped;

        for (int pass = 0; pass < n - 1; pass++) {
            swapped = false;

            for (int i = 0; i < n - 1 - pass; i++) {
                // comparator.compare() > 0 means array[i] should come
                // after array[i + 1] under this comparator's ordering.
                if (comparator.compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Helper method that swaps two elements in an array.
     *
     * @param <T>   the type of elements in the array
     * @param array the array containing the elements to swap
     * @param i     the index of the first element
     * @param j     the index of the second element
     */
    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
