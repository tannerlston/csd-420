/**
 * Tanner Elston 7/16/26, CSD420
 *
 * Product
 *
 * Represents an item in a store's inventory. Used to demonstrate a
 * practical, real-world use case for the two generic bubble sort
 * methods in BubbleSort.java: sorting a shopping cart or inventory
 * list by product name (natural ordering) or by price (custom
 * ordering via a Comparator).
 *
 * Implements Comparable so that Product objects have a natural
 * ordering: alphabetically by product name. This is a common default
 * for displaying inventory (e.g., in a catalog or search results
 * page). A separate Comparator can then be supplied to BubbleSort's
 * second sort() method to order the same products by price instead,
 * without changing this class at all. This is the benefit Comparator
 * provides over Comparable.
 *
 * SOURCE:
 *   The compareTo() implementation follows the standard guidance for
 *   implementing Comparable on a custom class, delegate to an
 *   existing Comparable field (here, the String name field, via
 *   String's own compareTo()) rather than writing manual comparison
 *   logic.
 *
 *     https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/
 *     (Effective Java, 3rd ed., Item 14: Consider implementing Comparable)
 */
public class Product implements Comparable<Product> {

    private String name;
    private double price;
    private int quantityInStock;

    public Product(String name, double price, int quantityInStock) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Defines the natural ordering of Product objects: alphabetically
     * by name. This matches how a customer-facing product catalog is
     * typically sorted by default.
     */
    @Override
    public int compareTo(Product other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f, qty: %d)", name, price, quantityInStock);
    }
}
