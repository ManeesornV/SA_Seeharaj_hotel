package ku.cs.shop.models;

import ku.cs.shop.services.PromotionSortByTimeComparator;

import java.util.*;

public class PromotionList {
    private ArrayList<Promotion> promotions;

    public PromotionList(){
       promotions = new ArrayList<>();
    }

    public ArrayList<Promotion> getPromotions(){ return promotions; }

    public void addPromotion(Promotion promotion){
        promotions.add(promotion);
        sort();
    }

    // เช็กว่าโค้ดโปรโมชั่นซ้ำหรือไม่
    public boolean checkCodePromotionHaveUsed(String codePromotion) {
        for (Promotion promotion: this.promotions) {
            if (promotion.getCodePromotion().equals(codePromotion)) {
                return true;
            }
        }
        return false;
    }

    //เช็กว่าโปรโมชั่นตรงกับร้านค้าหรือไม่
    public boolean checkCodePromotionIsCorrect(String shopName, String codePromotion) {
        for (Promotion promotion: this.promotions) {
            if (promotion.getShopName().equals(shopName) && promotion.getCodePromotion().equals(codePromotion)) {
                return true;
            }
        }
        return false;
    }

    // เช็กโปรโมชั่นที่มีในร้านค้าทั้งหมด
    public ArrayList<Promotion> getPromotionByShopName(String shopName) {
        ArrayList<Promotion> promotionByShopName = new ArrayList<>();
        for(Promotion promotion : promotions){
            if(promotion.getShopName().equals(shopName)){
                promotionByShopName.add(promotion);
            }
        }
        return promotionByShopName;
    }

    // ทำงานเมื่อมีการกรอกโปรโมชั่น
    public double usePromotion(ArrayList<Promotion> promotions, String promotionStr, double price){
        double rate = 0;
        for(Promotion promotion : promotions) {
            if(promotion.getCodePromotion().equals(promotionStr)){
                if(promotion.getRatePrice() <= price){
                    if(promotion.getPriceReductionInPercentage() != 0){
                        rate = price * promotion.getPriceReductionInPercentage() / 100;
                        price = price - rate;
                    }
                    if (promotion.getPriceReductionInBaht() != 0){
                        price = price - promotion.getPriceReductionInBaht();
                    }
                }
            }
        }
        return price;
    }

    public void sort(Comparator<Promotion> promotionComparator) {
        Collections.sort(this.promotions, promotionComparator);
    }

    public void sort() {
        PromotionSortByTimeComparator promotionSortByTimeComparator = new PromotionSortByTimeComparator();
        sort(promotionSortByTimeComparator);
    }

    public String toCsv() {
        String result = "" ;
        for (Promotion promotion : this.promotions) {
            result += promotion.toCsv() + "\n";
        }
        return result ;
    }
}
