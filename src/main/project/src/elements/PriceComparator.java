package src.elements;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class of comparator.
 * Compares products by the price.
 */

public class PriceComparator implements Comparator<Product>, Serializable {
    public int compare(Product p1, Product p2) {
        if (p1.getPrice() == p2.getPrice()) {
            return 0;
        }
        if (p1.getPrice() > p2.getPrice()) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
