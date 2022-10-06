package ku.cs.shop.models;

import ku.cs.shop.services.ProvideTypeBookDataSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Book {
    private String bookName;
    private String bookShop;
    private String bookAuthor;
    private String bookISBN;
    private String bookType;
    private String bookDetail;
    private String bookPublisher;
    private String bookImg;
    private int bookStock;
    private String bookPage;
    private int leastStock;
    private double bookPrice;
    private LocalDateTime timeOfAddingBook;
    private ArrayList<ProvideTypeBook> typeBookArrayList;

    //เก็บค่าเริ่มต้น
    public Book() {}

    public Book(String bookName, String bookShop, String bookAuthor, String bookISBN, String bookType,
                String bookDetail, String bookPublisher, String bookImg,
                int bookStock, String bookPage, int leastStock, double bookPrice, LocalDateTime timeOfAddingBook,ArrayList<ProvideTypeBook> typeBookArrayList) {
        this.bookName = bookName;
        this.bookShop = bookShop;
        this.bookAuthor = bookAuthor;
        this.bookISBN = bookISBN;
        this.bookType = bookType;
        this.bookDetail = bookDetail;
        this.bookPublisher = bookPublisher;
        this.bookImg = bookImg;
        this.bookStock = bookStock;
        this.bookPage = bookPage;
        this.leastStock = leastStock;
        this.bookPrice = bookPrice;
        this.timeOfAddingBook = timeOfAddingBook;
        this.typeBookArrayList = typeBookArrayList;
    }

    public Book(String bookName, double bookPrice, String bookShop) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookShop = bookShop;
    }

    public String getBookName() { return bookName; }
    public String getBookShop() { return bookShop; }
    public String getBookAuthor() { return bookAuthor; }
    public String getBookISBN() { return bookISBN; }
    public String getBookType() { return bookType; }
    public String getBookDetail() { return bookDetail; }
    public String getBookPublisher() { return bookPublisher; }
    public String getBookImg() { return bookImg; }
    public int getBookStock() { return bookStock; }

    public String getBookStatus()
    {
        if (bookStock > 0) return "พร้อมส่ง";
        return "สินค้าหมด";
    };

    public String getBookPage() { return bookPage; }
    public int getLeastStock() { return leastStock; }
    public double getBookPrice() { return bookPrice; }
    public LocalDateTime getTimeOfAddingBook() { return timeOfAddingBook; }
    public ArrayList<ProvideTypeBook> getTypeBookArrayList() { return typeBookArrayList; }

    public void setBookName(String bookName) { this.bookName = bookName; }
    public void setBookShop(String bookShop) { this.bookShop = bookShop; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }
    public void setBookISBN(String bookISBN) { this.bookISBN = bookISBN; }
    public void setBookType(String bookType) { this.bookType = bookType; }
    public void setBookDetail(String bookDetail) { this.bookDetail = bookDetail; }
    public void setBookPublisher(String bookPublisher) { this.bookPublisher = bookPublisher; }
    public void setBookImg(String bookImg) { this.bookImg = bookImg; }
    public void setBookStock(int bookStock) { this.bookStock = bookStock; }
    public void setLeastStock(int leastStock) { this.leastStock = leastStock; }
    public void setBookPrice(double bookPrice) { this.bookPrice = bookPrice; }
    public void setBookPage(String bookPage) { this.bookPage = bookPage; }
    public void setTimeOfAddingBook(LocalDateTime timeOfAddingBook) { this.timeOfAddingBook = timeOfAddingBook; }
    public void setTypeBookArrayList(ArrayList<ProvideTypeBook> typeBookArrayList) { this.typeBookArrayList = typeBookArrayList; }

    public String getPicturePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "images/book-images"
                + File.separator
                + bookImg).toURI().toString();
    }

    // increase method
    public void increaseStock () { this.setBookStock(this.bookStock + 1); }

    // decrease method
    public void decreaseStock () { this.setBookStock(this.bookStock - 1); }

    // ตรวจสอบข้อมูล bookISBN และส่งกลับไปยังเงื่อนไข
    public boolean isBookISBNCorrect(String bookISBN) {
        if (((bookISBN.length() == 10 || bookISBN.length() == 13 ) && Pattern.matches("[0-9]+",bookISBN)) || (bookISBN.length() == 1 ||Pattern.matches("[\\-]+",bookISBN)))  {
            return true;
        } else {
            return false;
        }
    }

    // ตรวจสอบข้อมูล bookISBN และแจ้งเตือน
    public String checkBookISBNCorrect(String bookISBN) {
        if (isBookISBNCorrect(bookISBN)) {
            return "";
        } else {
            return "รหัส ISBN นี้ไม่ถูกต้อง กรุณากรอกให้ครบ 10 หรือ 13 หลักเท่านั้น";
        }
    }

    // ตรวจสอบข้อมูลตัวเลขจำนวนเต็ม และส่งกลับไปยังเงื่อนไข
    public boolean isIntNumber(String num) {
        if ((Pattern.matches("[0-9]+",num)) && (Integer.parseInt(num) > 0)) {
            return true;
        } return false;
    }

    // ตรวจสอบข้อมูลตัวเลขจำนวนเต็ม และแจ้งเตือน
    public String checkIntNumber(String num) {
        if (isIntNumber(num)) {
            return "";
        } else {
            return "ข้อมูลผิดพลาด กรูณาใส่ตัวเลขจำนวนเต็มบวก";
        }
    }

    // ตรวจสอบข้อมูลตัวเลขจำนวนจริง และส่งกลับไปยังเงื่อนไข
    public boolean isDoubleNumber(String num) {
        if ((Pattern.matches("[0-9].+[0-9]+",num)||Pattern.matches("[0-9]+",num)) && (Double.parseDouble(num) > 0) ){
            return true;
        }return false;
    }

    // ตรวจสอบข้อมูลตัวเลขจำนวนจริง และแจ้งเตือน
    public String checkDoubleNumber(String num) {
        if (isDoubleNumber(num)) {
            return "";
        } else {
            return "ข้อมูลผิดพลาด กรูณาใส่ตัวเลขจำนวนทศนิยมเต็มบวก";
        }
    }

    // ตรวจสอบว่าข้อมูลได้ถูกกรอกทั้งหมดหรือไม่
    public boolean getDataCheck(Book book) {
        if (book.getBookName().equals("") || book.getBookShop().equals("") || book.getBookAuthor().equals("") || book.getBookISBN().equals("") || book.getBookType().equals("") || book.getBookDetail().equals("") ||
                book.getBookPublisher().equals("") || book.getBookStatus().equals("") || book.getBookImg().equals("") || (book.getBookStock() == -1) || book.getBookPage().equals("") || (book.getLeastStock() == -1) || (book.getBookPrice() == -1) ){
            return false;
        } else{ return true;}
    }

    // กำหนดค่าข้อมูล typeBook เริ่มต้น
    public void setTypeBook(Book book){
        ProvideTypeBookDataSource provideTypeBookDataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBookData.csv");
        ProvideTypeBookList typeBookList = provideTypeBookDataSource.readData();
        if(book.getTypeBookArrayList().size() != typeBookList.numOfSubTypeBook(book.getBookType())){
            ProvideTypeBook provideTypeBook = new ProvideTypeBook();
            for(int i = 0; i < typeBookList.numOfSubTypeBook(book.getBookType());i++){
                provideTypeBook.setSuperTypeBook(book.getBookType());
                provideTypeBook.setSubTypeBook("ไม่มีรายละเอียด");
                book.getTypeBookArrayList().add(provideTypeBook);
            }
        }
    }

    // คัดลอกรูปภาพ
    public static void copyImageToPackage(File image, String imageName) {
        File file = new File("images/book-images") ;
        Path desPath = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
        try {
            Files.copy(image.toPath(), desPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toCsv(){
        String result = "";
        String subType = "";
        for(int i = 0; i < typeBookArrayList.size(); i++){
            subType +=  "," + typeBookArrayList.get(i).getSubTypeBook();
        }

        result = "\"" + bookName.replace("\"","\"\"") + "\""  + ","
                + "\"" + bookShop.replace("\"","\"\"") + "\"" +  ","
                + "\"" + bookAuthor.replace("\"","\"\"")  + "\"" + ","
                + bookISBN + ","
                + "\"" + bookType.replace("\"","\"\"")  + "\"" + ","
                + "\"" + bookDetail.replace("\"","\"\"")  + "\"" + ","
                + "\"" + bookPublisher.replace("\"","\"\"") + "\"" + ","
                + "\"" + bookImg.replace("\"","\"\"") +  "\"" + ","
                + "\"" + Integer.toString(bookStock).replace(",","\"\"") + "\"" + ","
                + bookPage + ","
                + leastStock + ","
                + bookPrice + ","
                + timeOfAddingBook.toString() + subType + "\n" ;
        return result;
    }
}
