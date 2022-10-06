package ku.cs.shop.models;

import java.io.File;
import java.time.LocalDateTime;

public class Reviews {
    private String bookName;
    private String bookShop;
    private String userName;
    private String imageName ;
    private String comment;
    private int  bookRating;
    private LocalDateTime timeOfAddingReviews;

    public Reviews(){}

    public Reviews(String bookName, String bookShop, String userName, String imageName, String comment, int bookRating, LocalDateTime timeOfAddingReviews){
        this.bookName = bookName;
        this.bookShop = bookShop;
        this.userName = userName;
        this.imageName = imageName;
        this.comment = comment;
        this.bookRating = bookRating;
        this.timeOfAddingReviews = timeOfAddingReviews;
    }

    public String getBookName(){ return bookName; }
    public String getBookShop(){ return bookShop; }
    public String getUserName(){ return userName; }
    public String getImageName(){ return imageName; }
    public String getComment(){ return comment; }
    public int getBookRating(){ return bookRating; }
    public LocalDateTime getTimeOfAddingReviews() { return timeOfAddingReviews; }

    public void setUserName(String userName) { this.userName = userName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setBookRating(int  bookRating){ this.bookRating = bookRating; }
    public void setTimeOfAddingReviews(LocalDateTime timeOfAddingReviews){ this.timeOfAddingReviews = timeOfAddingReviews; }

    // คะแนนรีวิว
    public void addRatingPlusOne () { this.setBookRating(this.bookRating + 1); }
    public void addRatingPlusTwo () { this.setBookRating(this.bookRating + 2); }
    public void addRatingPlusThree () { this.setBookRating(this.bookRating + 3); }
    public void addRatingPlusFour () { this.setBookRating(this.bookRating + 4); }
    public void addRatingPlusFive () { this.setBookRating(this.bookRating + 5); }

    public int bookRating(int bookRating){
        if(bookRating > 5){
            bookRating = 5;
        }
        return bookRating;
    }

    public String getImagePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "images/account-images"
                + File.separator
                + imageName).toURI().toString();
    }

    public String toCsv(){
        return "\"" + bookName + "\"," + "\"" + bookShop + "\"," + userName + "," + imageName + "," + "\"" + comment + "\"" + "," + bookRating + "," + timeOfAddingReviews;
    }

    public String toString(){ return "\"ชื่อหนังสือ : " + bookName + " ชื่อร้านค้า : " + bookShop + " คะแนน : " + bookRating + " เวลา : " + timeOfAddingReviews + "\"";}
}