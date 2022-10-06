package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import ku.cs.shop.controllers.system.ChoiceApplySubtypeBookController;
import ku.cs.shop.controllers.system.StockController;
import ku.cs.shop.controllers.user.ChoiceProvideTypeBookController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.BookDetailDataSource;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.ProvideTypeBookDataSource;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ApplyBookController {
    @FXML private Button addImgButton;
    @FXML private TextField bookNameTextField;
    @FXML private TextField bookAuthorTextField;
    @FXML private TextField bookISBNTextField;
    @FXML private Button addBookButton;
    @FXML private TextField bookPageTextField;
    @FXML private Button goToSellerStockButton;
    @FXML private TextArea bookDetailTextArea;
    @FXML private TextField bookPublisherTextField;
    @FXML private TextField bookStockTextField;
    @FXML private TextField leastStockTextField;
    @FXML private TextField bookPriceTextField;
    @FXML private Label NotificationBookISBN;
    @FXML private Label NotificationBookPage;
    @FXML private Label NotificationBookStock;
    @FXML private Label NotificationLeastStock;
    @FXML private Label NotificationBookPrice;
    @FXML private Label NotificationCantAdd;
    @FXML private ImageView imageView;
    @FXML private MenuButton menuButton;
    @FXML private Button status;
    @FXML private Label usernameInHead;
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private ImageView userImageView;
    @FXML private FlowPane flowPaneSubTypeBook;

    private File selectedImage;
    private File selectedImageForCopy ;
    private String imageName;
    private String currentType;

    private ArrayList<ProvideTypeBook> typeBookArrayList = new ArrayList<>();
    private ArrayList<Account> accountsList = new ArrayList<>();

    private AccountList accountList ;
    private Account account ;

    private Book book = new Book();
    private BookDetailDataSource data ;
    private BookList books ;

    private ProvideTypeBookDataSource provideTypeBookDataSource;
    private ProvideTypeBookList typeBookList;

    public void initialize(){
        data = new BookDetailDataSource("csv-data/bookDetail.csv");
        books = data.readData();

        provideTypeBookDataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBookData.csv");
        typeBookList = provideTypeBookDataSource.readData();

        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
        userImageView.setImage(new Image(account.getImagePath()));

        pagesHeader();
        addBookTypeToMenuItem();
    }

    // ตรวจสอบข้อมูล bookISBN ที่รับเข้ามา
    @FXML public void handleKeyBookISBN(){
        book.setBookISBN(bookISBNTextField.getText());
        if(!book.isBookISBNCorrect(book.getBookISBN())){ book.setBookISBN(""); }
        NotificationBookISBN.setText(book.checkBookISBNCorrect(book.getBookISBN()));
    }

    // ตรวจสอบข้อมูล bookPage ที่รับเข้ามา
    @FXML public void handleKeyBookPage(){
        book.setBookPage(bookPageTextField.getText());
        if(! book.isIntNumber(book.getBookPage())){book.setBookPage("");}
        NotificationBookPage.setText(book.checkIntNumber(book.getBookPage()));
    }

    // ตรวจสอบข้อมูล bookStock ที่รับเข้ามา
    @FXML public void handleKeyBookStock(){
        if(! book.isIntNumber(bookStockTextField.getText())){book.setBookStock(-1);}
        else {book.setBookStock(Integer.parseInt(bookStockTextField.getText()));}
        NotificationBookStock.setText(book.checkIntNumber(bookStockTextField.getText()));
    }

    // ตรวจสอบข้อมูล leastStock ที่รับเข้ามา
    @FXML public void handleKeyLeastStock(){
        if(!book.isIntNumber(leastStockTextField.getText())){book.setLeastStock(-1);}
        else {book.setLeastStock(Integer.parseInt(leastStockTextField.getText()));}
        NotificationLeastStock.setText(book.checkIntNumber(leastStockTextField.getText()));
    }

    // ตรวจสอบข้อมูล bookPrice ที่รับเข้ามา
    @FXML public void handleKeyBookPrice(){
        if(!book.isDoubleNumber(bookPriceTextField.getText())){book.setBookPrice(-1);}
        else {book.setBookPrice(Double.parseDouble(bookPriceTextField.getText()));}
        NotificationBookPrice.setText(book.checkDoubleNumber(bookPriceTextField.getText()));
    }

    // เพิ่มประเภทหนังสือที่ปุ่ม
    public void addBookTypeToMenuItem() {
        for (String type : typeBookList.getSuperTypeBook()) {
            MenuItem subBookTypeMenuItem = new MenuItem(type);
            menuButton.getItems().add(subBookTypeMenuItem);
            subBookTypeMenuItem.setOnAction(this :: handleSubBookTypeMenuItem);
        }
    }

    // เปลี่ยนปุ่มให้มีชื่อตรงกับประเภทที่เลือก
    public void handleSubBookTypeMenuItem(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        changeBookType(menuItem.getText());
    }

    // เปลียนประเภทข้อมูลและเพิ่ม subTypeBook
    public void changeBookType(String type){
        typeBookArrayList.clear();
        flowPaneSubTypeBook.getChildren().clear();
        currentType = type;
        book.setBookType(type);
        menuButton.setText(type);

        ArrayList<ProvideTypeBook> provideTypeBookArrayList = typeBookList.findSubTypeBook(book.getBookType());
        int numTypeBookList = typeBookList.numOfSubTypeBook(book.getBookType());

        try {
            for (int i = 0; i < numTypeBookList ; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/choiceApplySubTypeBook.fxml"));

                flowPaneSubTypeBook.getChildren().add(fxmlLoader.load());
                ChoiceApplySubtypeBookController choiceApplySubtypeBookController = fxmlLoader.getController();
                choiceApplySubtypeBookController.setData(provideTypeBookArrayList.get(i),typeBookArrayList,accountList,i,typeBookList);
                choiceApplySubtypeBookController.changeData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // เพิ่มรูปจากการกดปุ่ม
    @FXML
    public void handleAddImageButton (ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null) {
            Image image = new Image(selectedImage.toURI().toString());
            imageView.setImage(image);
            selectedImageForCopy = selectedImage;
        }
        book.setBookImg("Haveimage");

    }

    // ตั้งชื่อรูปภาพและคัดลอกรูปภาพ
    public void setImageName() {
        if (selectedImageForCopy != null) {
            imageName =  bookNameTextField.getText() + "-"
                    + LocalDate.now().getYear() + "-"
                    + LocalDate.now().getMonth() + "-"
                    + LocalDate.now().getDayOfMonth() + "-"
                    + LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond() + ".png" ;
            book.copyImageToPackage(selectedImageForCopy , imageName) ;
        } else {
            imageName = "default.png" ;
        }
    }

    // เพิ่มข้อมูลและเช็คเงื่อนไขข้อมูล
    @FXML
    public void handleAddBookButton(ActionEvent actionEvent){
        setAddData();

        if (book.getDataCheck(book) && (book.isBookISBNCorrect(book.getBookISBN())) && (book.isIntNumber(book.getBookPage()))
                &&(book.isIntNumber(bookStockTextField.getText()))&&(book.isIntNumber(leastStockTextField.getText())) &&(book.isDoubleNumber(bookPriceTextField.getText()))
                && (selectedImageForCopy != null )) {

            setImageName();
            book.setBookImg(imageName);
            books.addBook(book);
            data.writeData(books);

            try {
                com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerStock ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        } else{
            NotificationCantAdd.setText("เพิ่มสินค้าไม่ได้ กรุณาตรวจสอบข้อมูลอีกครั้ง");
        }
    }

    // กำหนดข้อมูลที่เพิ่มเข้ามา
    public void setAddData(){
        book.setBookName(bookNameTextField.getText());
        book.setBookAuthor(bookAuthorTextField.getText());
        book.setBookDetail(bookDetailTextArea.getText());
        book.setBookPublisher(bookPublisherTextField.getText());
        book.setBookShop(account.getShopName());
        book.setTimeOfAddingBook(LocalDateTime.now());

        book.setBookISBN(bookISBNTextField.getText());
        book.setBookPage(bookPageTextField.getText());
        book.setBookStock(Integer.parseInt(bookStockTextField.getText()));
        book.setLeastStock(Integer.parseInt(leastStockTextField.getText()));
        book.setBookPrice(Double.parseDouble(bookPriceTextField.getText()));
        book.setTypeBookArrayList(typeBookArrayList);
        book.setTypeBook(book);
    }

    // กดปุ่มไปยัง sellerStock
    @FXML public void handleSellerStockButton(){
        try {
            com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า sellerStock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    // กดปุ่มไปยังข้อมูลส่วนตัว
    @FXML
    public void handleToAccountDetailButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("accountDetail" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า accountDetail ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    // กดปุ่มข้อมูลการขาย
    @FXML
    public void handleToSellerButton(ActionEvent actionEvent) {
        if (account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")) {
            try {
                com.github.saacsos.FXRouter.goTo("sellerHaventApply",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerHaventApply ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        else{
            try {
                com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerHaventApply ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    // กดปุ่มไปยังหน้าหนังสือทั้งหมด
    @FXML
    public void handleAllTypeBookButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // กำหนดและแสดงข้อมูลตรงส่วน head page
    public void pagesHeader() {
        usernameInHead.setText(account.getUserName());
        img.setImage(new Image(account.getImagePath()));
        if(account instanceof AdminAccount){
            status.setText("Admin");
        }else if(account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")){
            status.setText("User");
        }else {
            status.setText("Seller");
        }
    }

    // คลิกที่ logo แล้วจะไปหน้า home
    @FXML
    public void mouseClickedInLogo(MouseEvent event){
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ออกจากระบบกลับไปล็อกอิน
    @FXML
    public void handleToLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    // กดปุ่มไปยังประวัติการซื้อ
    @FXML
    public void handleToOrderPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("bookOrderOfUser" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
