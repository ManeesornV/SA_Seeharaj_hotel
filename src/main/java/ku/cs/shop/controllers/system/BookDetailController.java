package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import ku.cs.shop.models.*;
import ku.cs.shop.services.ProvideTypeBookDataSource;
import ku.cs.shop.services.ReviewsDataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookDetailController
{
    @FXML private Label bookNameLabel;
    @FXML private Label bookShop;
    @FXML private Label bookStatus;
    @FXML private Label bookDetail;
    @FXML private Label bookType;
    @FXML private Label bookISBN;
    @FXML private Label bookPage;
    @FXML private Label bookPublisher;
    @FXML private Label bookAuthor;
    @FXML private Label bookPrice;
    @FXML private Label typeLabel;
    @FXML private Label usernameInHead;
    @FXML private Button status;
    @FXML private FlowPane commentFlowPane;
    @FXML private ImageView bookImg;
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private TextField commentTextField;
    @FXML private GridPane showPopupGrid;
    @FXML private Label numberOfComments;
    @FXML private Label bookRatingLabel;
    @FXML private FlowPane showTypeBookFlowPane;

    private Reviews reviews = new Reviews();
    private AccountList accountList;
    private Account account;
    private Book book;
    private BookList bookList;
    private ReviewsList reviewsList;
    private ReviewsDataSource reviewsDataSource;
    private ProvideTypeBookDataSource provideTypeBookDataSource;
    private ProvideTypeBookList provideTypeBookList;
    private String imageName;
    private ArrayList<Object> objectForPassing = new ArrayList<>();

    @FXML
    public void initialize()
    {
        objectForPassing = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        reviewsDataSource = new ReviewsDataSource("csv-data/reviews.csv");
        provideTypeBookDataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBookData.csv");
        reviewsList = reviewsDataSource.readData();
        provideTypeBookList = provideTypeBookDataSource.readData();
        castObjectToData();
        showData();
        pagesHeader();
        addInfoToShowTypeBookFlowPane();
        showCommentByBookNameAndShop(book.getBookName(), book.getBookShop());
        numberOfComments.setText("(" + reviewsList.getCountBookByNameAndShop(book.getBookName(), book.getBookShop()) + ")");
    }

    public void castObjectToData() {
        book = (Book) objectForPassing.get(3);
        bookList = (BookList) objectForPassing.get(0);
        account = (Account) objectForPassing.get(1);
        accountList = (AccountList) objectForPassing.get(2);
    }

    public Book getBook() {
        return book;
    }
    public Account getAccount() { return account; }

    public void showData() {
        bookNameLabel.setText(book.getBookName());
        bookShop.setText(book.getBookShop());
        bookStatus.setText(book.getBookStatus());
        bookDetail.setText(book.getBookDetail());
        bookType.setText(book.getBookType());
        bookISBN.setText(book.getBookISBN());
        bookPage.setText(book.getBookPage());
        bookPublisher.setText(book.getBookPublisher());
        bookAuthor.setText(book.getBookAuthor());
        typeLabel.setText(book.getBookType());
        bookImg.setImage(new Image(book.getPicturePath()));
        bookPrice.setText(String.format("%.02f",book.getBookPrice()) + " Baht.");
    }

    public ArrayList<Object> castDataToObject() {
        objectForPassing.clear();
        objectForPassing.add(book);
        objectForPassing.add(bookList);
        objectForPassing.add(account);
        objectForPassing.add(accountList);

        return objectForPassing;
    }

    public BookList getBookList() {
        return bookList;
    }

    // กำหนดและแสดงข้อมูลตรงส่วน head page
    public void pagesHeader() {
        usernameInHead.setText(account.getUserName());
        img.setImage(new Image(account.getImagePath()));

        if (account instanceof AdminAccount){
            status.setText("Admin");
        } else if (account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")) {
            status.setText("User");
        } else {
            status.setText("Seller");
        }
    }

    // แสดงหมวดหมู่เพิ่มเติมของหนังสือเล่มนั้น
    public void addInfoToShowTypeBookFlowPane() {
        ArrayList<ProvideTypeBook> provideTypeBooks = provideTypeBookList.findSubTypeBook(book.getBookType());
        int numOfSubTypeBook = provideTypeBookList.numOfSubTypeBook(book.getBookType());
        try {
            for(int i = 0  ; i < numOfSubTypeBook ; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/showSubType.fxml"));

                showTypeBookFlowPane.getChildren().add(fxmlLoader.load());
                ShowSubTypeController showSubTypeController = fxmlLoader.getController();
                showSubTypeController.changeData(provideTypeBooks,book,i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    // ไปยังหน้ารายละเอียดส่วนตัว
    public void handleToInformationButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("accountDetail", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // แสดง pop-up เพื่อซื้อหนังสือ
    @FXML
    public void handleToBuyBook(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ku/cs/buyBookPopup.fxml"));

            showPopupGrid.add(fxmlLoader.load(), 0, 0);
            ConfirmOrderController confirmOrderController = fxmlLoader.getController();
            confirmOrderController.setController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public void handleClosePage() {
        showPopupGrid.getChildren().remove(0);
    }

    @FXML
    public void handleBackToMarket(ActionEvent event) {
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // แสดงหน้าร้านค้า
    @FXML
    public void handleToBookSortFromShop(ActionEvent actionevent) {
        try {
            com.github.saacsos.FXRouter.goTo("pageBookShop", castDataToObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAllTypeBookButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Logo Javapai ไปยังหน้า Home
    @FXML
    public void mouseClickedInLogo(){
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // comment
    public void showCommentByBookNameAndShop(String bookName, String bookShop) { //แสดง comment
        commentFlowPane.getChildren().clear();
        ArrayList<Reviews> bookByNameAndShop = reviewsList.getReviewsByBookNameAndShopName(bookName, bookShop);
        try {
            for (Reviews reviews : bookByNameAndShop) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/itemReview.fxml"));

                commentFlowPane.getChildren().add(fxmlLoader.load());
                ItemReviewController itemReviewController = fxmlLoader.getController();
                itemReviewController.setReviewData(reviews);
                bookRatingLabel.setText(String.valueOf(String.format("%.02f",reviewsList.averageRating(reviews.getBookName(), reviews.getBookShop()))));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendReviewToWrite(){
        Reviews reviews = new Reviews(
                book.getBookName(),
                book.getBookShop(),
                account.getUserName(),
                imageName,
                commentTextField.getText(),
                addRating(),
                LocalDateTime.now()
        );

        reviewsList.addReviews(reviews);
        reviewsDataSource.writeData(reviewsList);
    }

    public void setImageName() {
        imageName = account.getImageName() ;
    }

    // ให้คะแนนหนังสือเล่มนั้น 1 คะแนน
    @FXML
    public void handleAddRatingPlusOneButton(ActionEvent actionEvent) {
        reviews.addRatingPlusOne();
        addRating();
    }

    // ให้คะแนนหนังสือเล่มนั้น 2 คะแนน
    @FXML
    public void handleAddRatingPlusTwoButton(ActionEvent actionEvent) {
        reviews.addRatingPlusTwo();
        addRating();
    }

    // ให้คะแนนหนังสือเล่มนั้น 3 คะแนน
    @FXML
    public void handleAddRatingPlusThreeButton(ActionEvent actionEvent) {
        reviews.addRatingPlusThree();
        addRating();
    }

    // ให้คะแนนหนังสือเล่มนั้น 4 คะแนน
    @FXML
    public void handleAddRatingPlusFourButton(ActionEvent actionEvent) {
        reviews.addRatingPlusFour();
        addRating();
    }

    // ให้คะแนนหนังสือเล่มนั้น 5 คะแนน
    @FXML
    public void handleAddRatingPlusFiveButton(ActionEvent actionEvent) {
        reviews.addRatingPlusFive();
        addRating();
    }
    public int addRating(){
        return reviews.bookRating(reviews.getBookRating());
    }

    // บันทึก reviews ลง csv
    @FXML
    public void handleSendCommentButton(ActionEvent actionEvent) {
        setImageName();
        sendReviewToWrite();
        try {
            com.github.saacsos.FXRouter.goTo("bookDetail", objectForPassing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //report
    @FXML
    public void handleToReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("reportingInBookDetail" ,castDataToObject());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}