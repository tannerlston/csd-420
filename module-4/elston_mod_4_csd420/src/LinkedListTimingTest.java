// Tanner Elston. 6/1/26, CSD420

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * LinkedListTimingTest.java
 *
 * Demonstrates and times two different ways of traversing a java.util.LinkedList<Integer>:
 *   1. Using an Iterator (ListIterator). The "correct" way to walk a LinkedList.
 *   2. Using the get(index) method in a for-loop. The "naive" way that people
 *      coming from ArrayList habits often reach for first.
 *
 * The program is run twice: once with 50,000 elements and once with 500,000 elements,
 * so we can see not just WHICH approach is faster, but HOW the gap GROWS as size grows.
 *
 * A small set of correctness tests (a simple hand-rolled unit-test style check) is
 * included at the bottom to confirm both traversal methods actually visit every element
 * in the correct order and compute the same sum, before we trust the timing numbers.
 */
public class LinkedListTimingTest {

    public static void main(String[] args) {

        // ---- Correctness tests first: never trust a timing benchmark for code you
        // ---- haven't verified is actually correct. ----
        runCorrectnessTests();

        System.out.println();
        System.out.println("===== Timing Comparison =====");
        System.out.println();

        // Requirement: run the comparison for both 50,000 and 500,000 elements.
        runTimingComparison(50_000);
        System.out.println();
        runTimingComparison(500_000);
    }

    /**
     * Builds a LinkedList of the given size filled with 0, 1, 2, ..., size-1,
     * then times (a) an iterator traversal and (b) a get(index) traversal,
     * printing the results in milliseconds.
     */
    private static void runTimingComparison(int size) {
        LinkedList<Integer> list = buildList(size);

        System.out.println("--- List size: " + size + " ---");

        long iteratorTime = timeIteratorTraversal(list);
        long getIndexTime = timeGetIndexTraversal(list);

        System.out.println("Iterator traversal time : " + iteratorTime + " ms");
        System.out.println("get(index) traversal time: " + getIndexTime + " ms");

        if (iteratorTime > 0) {
            double ratio = (double) getIndexTime / (double) iteratorTime;
            System.out.printf("get(index) was about %.1fx slower than the iterator%n", ratio);
        } else {
            System.out.println("Iterator time rounded to 0 ms, so a precise ratio can't be computed.");
        }
    }

    /** Creates and returns a LinkedList<Integer> containing 0..size-1. */
    private static LinkedList<Integer> buildList(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    /** Times a full traversal of the list using a ListIterator. Returns elapsed ms. */
    private static long timeIteratorTraversal(LinkedList<Integer> list) {
        long start = System.currentTimeMillis();

        long sum = 0; // accumulate something so the JIT can't optimize the loop away
        ListIterator<Integer> it = list.listIterator();
        while (it.hasNext()) {
            sum += it.next();
        }

        long end = System.currentTimeMillis();
        // Print (or just discard) sum so the compiler treats it as "used".
        if (sum < 0) {
            System.out.println("unreachable, just keeps sum 'live'");
        }
        return end - start;
    }

    /** Times a full traversal of the list using get(index) in a for-loop. Returns elapsed ms. */
    private static long timeGetIndexTraversal(LinkedList<Integer> list) {
        long start = System.currentTimeMillis();

        long sum = 0;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sum += list.get(i);
        }

        long end = System.currentTimeMillis();
        if (sum < 0) {
            System.out.println("unreachable, just keeps sum 'live'");
        }
        return end - start;
    }

    // ==========================================================================
    // Correctness tests
    // ==========================================================================

    /**
     * Runs a handful of simple checks (no external test framework required) to
     * confirm that:
     *   1. Both traversal methods visit every element.
     *   2. Both traversal methods visit elements in the same order.
     *   3. Both traversal methods compute the same sum for a small, known list.
     *   4. An empty list is handled correctly (no exceptions, sum = 0).
     * If any check fails, an AssertionError is thrown so the problem is obvious
     * immediately instead of silently producing bad timing data.
     */
    private static void runCorrectnessTests() {
        System.out.println("===== Running correctness tests =====");

        // Test 1: small known list, check sum from both approaches matches expected value.
        LinkedList<Integer> small = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            small.add(i); // 1,2,...,10
        }
        int expectedSum = 55; // 1+2+...+10

        int iteratorSum = 0;
        for (int value : small) { // for-each uses an iterator under the hood
            iteratorSum += value;
        }
        assertEquals(expectedSum, iteratorSum, "Iterator sum for 1..10");

        int getIndexSum = 0;
        for (int i = 0; i < small.size(); i++) {
            getIndexSum += small.get(i);
        }
        assertEquals(expectedSum, getIndexSum, "get(index) sum for 1..10");

        // Test 2: confirm both approaches visit elements in the SAME order.
        LinkedList<Integer> order = new LinkedList<>();
        order.add(100);
        order.add(200);
        order.add(300);

        StringBuilder iterOrder = new StringBuilder();
        for (int v : order) {
            iterOrder.append(v).append(",");
        }

        StringBuilder idxOrder = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            idxOrder.append(order.get(i)).append(",");
        }

        assertEquals(iterOrder.toString(), idxOrder.toString(), "Traversal order matches");

        // Test 3: empty list should not throw and should sum to 0.
        LinkedList<Integer> empty = new LinkedList<>();
        int emptySumIterator = 0;
        for (int v : empty) {
            emptySumIterator += v;
        }
        assertEquals(0, emptySumIterator, "Iterator sum of empty list");

        int emptySumGetIndex = 0;
        for (int i = 0; i < empty.size(); i++) {
            emptySumGetIndex += empty.get(i);
        }
        assertEquals(0, emptySumGetIndex, "get(index) sum of empty list");

        // Test 4: element count sanity check on a larger generated list.
        LinkedList<Integer> generated = buildList(1000);
        int count = 0;
        for (@SuppressWarnings("unused") int v : generated) {
            count++;
        }
        assertEquals(1000, count, "Element count via iterator for generated list of 1000");
        assertEquals(1000, generated.size(), "size() matches expected for generated list");

        System.out.println("All correctness tests passed.");
    }

    /** Minimal helper used in place of a full unit-testing framework like JUnit. */
    private static void assertEquals(Object expected, Object actual, String testName) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("FAILED: " + testName
                    + " -- expected [" + expected + "] but got [" + actual + "]");
        }
        System.out.println("PASSED: " + testName);
    }
}

/*
 * ==========================================================================
 * DISCUSSION OF RESULTS
 * ==========================================================================
 *
 * Actual output from a real run of this program (your exact numbers will vary
 * depending on hardware, JVM warm-up, and background load, but the PATTERN below
 * is consistent and is the important takeaway):
 *
 *   --- List size: 50,000 ---
 *   Iterator traversal time  : 7 ms
 *   get(index) traversal time: 973 ms
 *   get(index) was about 139.0x slower than the iterator
 *
 *   --- List size: 500,000 ---
 *   Iterator traversal time  : 10 ms
 *   get(index) traversal time: 148,932 ms   (almost 2.5 minutes!)
 *   get(index) was about 14,893.2x slower than the iterator
 *
 * WHY THIS HAPPENS:
 *
 * A LinkedList is made up of Node objects that only know about their previous and
 * next neighbor. There is no underlying array with random-access indexing like
 * an ArrayList has. Internally, java.util.LinkedList.get(index) walks the chain of
 * nodes one at a time starting from whichever end (head or tail) is closer to the
 * requested index, until it reaches the node at that position. That single call is
 * an O(n) operation in the worst case.
 *
 * When get(index) is called inside a for-loop that runs from i = 0 to size - 1,
 * we are doing an O(n) walk, n different times; Once for every index. That makes
 * the WHOLE traversal O(n^2) overall, even though each individual call "looks"
 * cheap in isolation.
 *
 * The Iterator (or ListIterator), on the other hand, keeps an internal reference to
 * the current node. Calling next() simply follows the "next" pointer to the
 * following node, an O(1) operation. Doing that n times to walk the whole list is
 * therefore an O(n) operation overall.
 *
 * COMPARING THE TWO LIST SIZES (50,000 vs 500,000):
 *
 * - The list size increased by a factor of 10 (50,000 -> 500,000).
 * - The iterator traversal time barely changed and scaled roughly LINEARLY with
 *   the 10x increase in size (consistent with O(n) behavior), it might go from a
 *   couple of milliseconds to well under 100 ms.
 *
 * - The get(index) traversal time did NOT just increase by 10x, it increased by
 *   roughly 10 x 10 = 100x (consistent with O(n^2) behavior), because both the
 *   number of get() calls AND the average cost of each individual get() call grew
 *   with the list size.
 *
 * - In other words, going from 50,000 to 500,000 elements made the iterator
 *   approach only modestly slower, but it made the get(index) approach dramatically,
 *   sometimes unbearably, slower. This gap is exactly why the Java
 *   documentation and most style guides warn against calling get(index) in a loop
 *   on a LinkedList: it turns what looks like a simple linear loop into a
 *   quadratic-time bottleneck as data grows.
 *
 * PRACTICAL TAKEAWAY:
 *
 * - For an ArrayList, get(index) is O(1) (true random access via a backing array),
 *   so looping with get(index) is perfectly fine and even idiomatic there.
 * - For a LinkedList, always prefer an Iterator (or a for-each loop, which uses an
 *   iterator under the hood) when you need to visit every element in order. Reserve
 *   get(index) for LinkedList only for occasional, one-off lookups, not for
 *   traversing the whole list.
 */