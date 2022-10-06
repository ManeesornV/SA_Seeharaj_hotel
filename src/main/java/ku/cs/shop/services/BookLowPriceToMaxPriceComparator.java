package ku.cs.shop.services;

import ku.cs.shop.models.Book;

import java.util.Comparator;

public class BookLowPriceToMaxPriceComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        if (o1.getBookPrice() > o2.getBookPrice()) return 1;
        if (o1.getBookPrice() < o2.getBookPrice()) return -1;
        return 0;
    }
}
