package ku.cs.shop.controllers.user;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ku.cs.shop.controllers.system.StockController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.AccountDataSource;
import ku.cs.shop.services.BookDetailDataSource;
import ku.cs.shop.services.ProvideTypeBookDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ProvideTypeBookController {
    @FXML private ImageView logoJavaPai;
    @FXML private Button provideUserButton;
    @FXML private Button provideShopButton;
    @FXML private Label topicLabel;
    @FXML private Label mainTopicLabel1;
    @FXML private Label mainTopicLabel;
    @FXML private Label topicLabel1;
    @FXML private TextField newBooktypeTextField;
    @FXML private Button checkBookButton;
    @FXML private Label notificationCheckTypeBookLabel;
    @FXML private Label topicLabel11;
    @FXML private Button addSubTypeBookButton;
    @FXML private Button saveSubtypeBookButton;
    @FXML private FlowPane flowPaneForSubTypeBook;

    private AccountList accountList ;
    private Account account ;
    private UserAccount selectedAccount ;

    private ProvideTypeBookDataSource provideTypeBookDataSource ;
    private ProvideTypeBookList typeBookList ;
    private ProvideTypeBook provideTypeBook = new ProvideTypeBook("","");

    private BookDetailDataSource data ;
    private BookList books;

    public void initialize(){
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;

        data = new BookDetailDataSource("csv-data/bookDetail.csv");
        books = data.readData();

        provideTypeBookDataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBookData.csv");
        typeBookList = provideTypeBookDataSource.readData();
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

    // ตรวจสอบข้อมูล typeBook ที่รับมา
    @FXML
    public void handleKeyNewBookTypeTextField(){
        if(typeBookList.checkNewTypeBookHaveUsed(newBooktypeTextField.getText())){
            notificationCheckTypeBookLabel.setText("มีประเภทหนังสือนี้อยู่แล้ว กรุณากรอกประเภทหนังสือใหม่");
        }
        else{
            notificationCheckTypeBookLabel.setText("");
        }
    }

    // กดเพื่อเพิ่มข้อมูล typeBook
    @FXML
    public void handleEnterTypeBookButton(ActionEvent actionEvent){
        if (typeBookList.checkNewTypeBookHaveUsed(newBooktypeTextField.getText())) {
            notificationCheckTypeBookLabel.setText("กรุณากรอกประเภทหนังสือ");
        }
        else{
            provideTypeBook.setSuperTypeBook(newBooktypeTextField.getText());
            notificationCheckTypeBookLabel.setTextFill(Color.GREEN);
            notificationCheckTypeBookLabel.setText("ประเภทหนังสือนี้สามารถเพิ่มได้");
        }
    }

    // กดปุ่มเพื่อยังหน้าการจัดการผู้ใช้
    @FXML
    public void handleToProvideAdminButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("forAdmin" ,accountList);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า provideTypeBookByAdmin ไม่ได้");
            e.printStackTrace();
        }
    }

    // กดเพื่อเพิ่ม subTypeBook
    int numSubType = 1;
    @FXML
    public void handleAddSubTypeBookButton(ActionEvent actionEvent) throws IOException {
        if (! provideTypeBook.getSuperTypeBook().equals("")) {
            notificationCheckTypeBookLabel.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ku/cs/choiceProvideTypeBookByAdmin.fxml"));
            flowPaneForSubTypeBook.getChildren().add(fxmlLoader.load());

            ChoiceProvideTypeBookController choiceProvideTypeBookController = fxmlLoader.getController();
            choiceProvideTypeBookController.setData(provideTypeBook,typeBookList,numSubType++);
        }
        else{
            notificationCheckTypeBookLabel.setText("กรุณากรอกประเภทของหนังสือ");
        }
    }

    // กดเพื่อเคลียร์หน้าข้อมูล
    @FXML
    public void handleSaveTypeBookButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("provideTypeBookByAdmin" ,accountList);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า provideTypeBookByAdmin ไม่ได้");
            e.printStackTrace();
        }
    }

    // กดปุ่มเพื่อไปยังหน้าข้อมูลส่วนตัว
    @FXML
    public void handleToInformationButton(ActionEvent actionEvent) {
        try {
            AccountDataSource accountDataSource = new AccountDataSource("csv-data/accountData.csv") ;
            accountDataSource.writeData(accountList);
            com.github.saacsos.FXRouter.goTo("accountDetail", accountList);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า detailUser ไม่ได้");
            e.printStackTrace();
        }
    }
}
