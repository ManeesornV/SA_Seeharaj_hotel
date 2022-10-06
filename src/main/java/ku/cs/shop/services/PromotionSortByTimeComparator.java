package ku.cs.shop.services;

import ku.cs.shop.models.Promotion;
import java.util.Comparator;

public class PromotionSortByTimeComparator implements Comparator<Promotion> {
    @Override
    public int compare(Promotion o1, Promotion o2) {
        if(o1.getTimeOfAddingPromotion().compareTo(o2.getTimeOfAddingPromotion()) < 0) return 1;
        if(o1.getTimeOfAddingPromotion().compareTo(o2.getTimeOfAddingPromotion()) > 0) return -1;
        return 0;
    }
}
