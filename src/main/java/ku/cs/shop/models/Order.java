package ku.cs.shop.models;

import java.io.File;
import java.time.LocalDateTime;

public class Order {
    private String bookImage;
    private String bookName;
    private String bookShop;
    private int totalBookOrdered;
    private double totalPriceOrdered;
    private String trackingNumber;
    private String customerName;
    private String customerPhone;
    private LocalDateTime timeOfOrdered;

    public Order(){}

    public Order(String bookImage, String bookName, String bookShop, int totalBookOrdered, double totalPriceOrdered
            , String trackingNumber, String customerName, String customerPhone, LocalDateTime timeOfOrdered){
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.bookShop = bookShop;
        this.totalBookOrdered = totalBookOrdered;
        this.totalPriceOrdered = totalPriceOrdered;
        this.trackingNumber = trackingNumber;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.timeOfOrdered = timeOfOrdered;
    }

    public String getBookImage() { return bookImage; }
    public String getBookName() { return bookName; }
    public String getBookShop() { return bookShop; }
    public int getTotalBookOrdered() { return totalBookOrdered; }
    public double getTotalPriceOrdered() { return totalPriceOrdered; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public LocalDateTime getTimeOfOrdered() { return timeOfOrdered; }

    public void setBookImage(String bookImage) { this.bookImage = bookImage; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public void setBookShop(String bookShop) { this.bookShop = bookShop; }
    public void setTotalBookOrdered(int totalBookOrdered) { this.totalBookOrdered = totalBookOrdered; }
    public void setTotalPriceOrdered(double totalPriceOrdered) { this.totalPriceOrdered = totalPriceOrdered; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public void setTimeOfOrdered(LocalDateTime timeOfOrdered) { this.timeOfOrdered = timeOfOrdered; }

    public String getPicturePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "images/book-images"
                + File.separator
                + bookImage).toURI().toString();
    }

    public String toCsv(){
        String result = "";
        result = "\"" + bookImage.replace("\"","\"\"") + "\""  + ","
                + "\"" + bookName.replace("\"","\"\"") + "\""  + ","
                + "\"" + bookShop.replace("\"","\"\"") + "\""  + ","
                + totalBookOrdered + ","
                + totalPriceOrdered  + ","
                + "\"" + trackingNumber.replace("\"","\"\"") + "\""  + ","
                + "\"" + customerName.replace("\"","\"\"") + "\""  + ","
                + "\"" + customerPhone.replace("\"","\"\"") + "\""  + ","
                + timeOfOrdered.toString() + "\n" ;

        return result;
    }
}
