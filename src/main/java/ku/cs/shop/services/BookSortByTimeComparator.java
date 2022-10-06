package ku.cs.shop.services;

import ku.cs.shop.models.Book;

import java.util.Comparator;

public class BookSortByTimeComparator implements Comparator<Book> {


    @Override
    public int compare(Book o1, Book o2) {
        if (o1.getTimeOfAddingBook().compareTo( o2.getTimeOfAddingBook()) < 0)  return 1;
        if (o1.getTimeOfAddingBook().compareTo( o2.getTimeOfAddingBook()) > 0)  return -1;
        return 0;
    }
}
