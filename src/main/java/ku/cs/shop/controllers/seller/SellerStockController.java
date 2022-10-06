package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import ku.cs.shop.controllers.system.StockController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.BookDetailDataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellerStockController implements Initializable {
    @FXML private ScrollPane scoll;
    @FXML private Button orderListButtonn;
    @FXML private Button addStockButton;
    @FXML private Button status;
    @FXML private Label usernameInHead;
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private ImageView userImageView;
    @FXML private FlowPane flowPane;

    private BookDetailDataSource data = new BookDetailDataSource("csv-data/bookDetail.csv");
    private BookList books = data.readData();

    private ArrayList<Account> accountsList = new ArrayList<>();
    private AccountList accountList ;
    private Account account ;

    public void initialize (URL location, ResourceBundle resource){
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
        userImageView.setImage(new Image(account.getImagePath()));

        pagesHeader();

        ArrayList<Book> bookInShop = books.getBookByShop(account.getShopName());

        try {
            for (int i = 0; i < books.getCountBookByShop(account.getShopName()) ; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/stock.fxml"));

                flowPane.getChildren().add(fxmlLoader.load());
                StockController stockController = fxmlLoader.getController();
                stockController.setData(bookInShop.get(i),accountList,books);
                stockController.changeData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // กดปุ่มไปยังหน้าเพิ่มหนังสือ
    @FXML
    public void handleAddStockButton(){
        try {
            com.github.saacsos.FXRouter.goTo("applyBook",accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า applyBook ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    // กดปุ่มไปยังรายการสั่งซื้อ
    @FXML
    public void handleOrderListButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("orderList",accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า orderList ไม่ได้");
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
            }
        }
        else{
            try {
                com.github.saacsos.FXRouter.goTo("sellerStock",accountList);
            } catch (IOException e) {
                e.printStackTrace();
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

    //คลิกที่รูป logo แล้วจะไปหน้า home
    @FXML
    public void mouseClickedInLogo(MouseEvent event){
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
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

    // กดปุ่มไปยังหน้าสร้างโปรโมชั่น
    @FXML
    public void handleToPromotionPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("promotion" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
