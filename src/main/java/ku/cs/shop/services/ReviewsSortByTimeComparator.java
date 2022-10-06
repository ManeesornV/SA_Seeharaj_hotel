package ku.cs.shop.services;
import ku.cs.shop.models.Reviews;
import java.util.Comparator;

public class ReviewsSortByTimeComparator implements Comparator<Reviews> {
    @Override
    public int compare(Reviews o1, Reviews o2) {
        if(o1.getTimeOfAddingReviews().compareTo(o2.getTimeOfAddingReviews()) < 0) return 1;
        if(o1.getTimeOfAddingReviews().compareTo(o2.getTimeOfAddingReviews()) > 0) return -1;
        return 0;
    }
}
