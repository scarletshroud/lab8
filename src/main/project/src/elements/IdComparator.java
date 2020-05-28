package src.elements;

import java.util.Comparator;

public class IdComparator implements Comparator<Product> {
    public int compare(Product p1, Product p2) {
        if (p1.getId() == p2.getId()) {
            return 0;
        }
        if (p1.getId() > p2.getId()) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
