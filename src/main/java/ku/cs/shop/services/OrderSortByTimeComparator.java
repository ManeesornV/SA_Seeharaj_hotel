package ku.cs.shop.services;

import ku.cs.shop.models.Order;

import java.util.Comparator;

public class OrderSortByTimeComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getTimeOfOrdered().compareTo(o2.getTimeOfOrdered()) > 0) return -1;
        if (o1.getTimeOfOrdered().compareTo(o2.getTimeOfOrdered()) < 0) return 1;
        return 0;
    }
}
