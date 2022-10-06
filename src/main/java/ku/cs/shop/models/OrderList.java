package ku.cs.shop.models;

import ku.cs.shop.services.BookSortByTimeComparator;
import ku.cs.shop.services.OrderSortByTimeComparator;

import java.util.*;

public class OrderList {
    private ArrayList<Order> orders;
    private Set<String> customerName;
    private Set<String> shopName;

    public OrderList() {
        orders = new ArrayList<>();
        customerName = new HashSet<>();
        shopName = new HashSet<>();
    }

    public void addOrder(Order order){
        orders.add(order);
        customerName.add(order.getCustomerName());
        shopName.add(order.getBookShop());
        sort();
    }

    public Order getOrder(int index) { return orders.get(index); }

    public Set<String> getShopName() { return shopName; }

    public ArrayList<Order> getOrderByShop(String shopName){
        ArrayList<Order> orderByShop = new ArrayList<>();
        for (Order order : orders){
            if (order.getBookShop().equals(shopName)){
                orderByShop.add(order);
            }
        }
        return orderByShop;
    }

    public int getCountOrderByShop(String shopName){
        int count = 0;
        for (Order order : orders){
            if (order.getBookShop().equals(shopName)){
                count++;
            }
        }
        return count;
    }

    public ArrayList<Order> getOrderByCustomerName(String name) {
        ArrayList<Order> orderByName = new ArrayList<>();
        for(Order order : orders) {
            if (order.getCustomerName().equals(name)) {
                orderByName.add(order);
            }
        }
        return orderByName;
    }

    public String toCSV() {
        String csv = "" ;
        for(Order order : orders) {
            csv += order.toCsv();
        }
        return csv;
    }

    public void sort(Comparator<Order> orderComparator) {
        Collections.sort(this.orders, orderComparator);
    }

    public void sort(){
        OrderSortByTimeComparator orderSortByTimeComparator = new OrderSortByTimeComparator();
        sort(orderSortByTimeComparator);
    }
    public int getOrderListCount(){ return this.orders.size(); }
}
