package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import ku.cs.shop.models.*;
import ku.cs.shop.services.PromotionDataSource;
import java.io.IOException;
import java.util.ArrayList;

public class PromotionToShowUserController {
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private Button status;
    @FXML private Label usernameInHead;

    @FXML private FlowPane promotionListFlowPane;
    @FXML private Label shopNameLabel;

    private PromotionList promotionList;
    private AccountList accountList ;
    private Account account;
    private Book book;
    private BookList bookList;
    private PromotionDataSource promotionDataSource;
    private ArrayList<Object> objectForPassing  = new ArrayList<>();

    public void initialize(){
        objectForPassing = (ArrayList<Object>) com.github.saacsos.FXRouter.getData();
        castObjectToData();
        promotionDataSource = new PromotionDataSource("csv-data/promotion.csv");
        promotionList = promotionDataSource.readData();
        pagesHeader();
        shopNameLabel.setText(book.getBookShop());
        showPromotionByShop(book.getBookShop());
    }

    public void castObjectToData() {
        book = (Book) objectForPassing.get(0);
        bookList = (BookList) objectForPassing.get(1);
        account = (Account) objectForPassing.get(2);
        accountList = (AccountList) objectForPassing.get(3);
    }

    // แสดงโปรโมชั่น
    public void showPromotionByShop(String shopName) {
        promotionListFlowPane.getChildren().clear();
        ArrayList<Promotion> promotionByShopName = promotionList.getPromotionByShopName(shopName);
        try {
            for (Promotion promotion : promotionByShopName) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/itemPromotionForShowUser.fxml"));

                promotionListFlowPane.getChildren().add(fxmlLoader.load());
                ItemPromotionController itemPromotionController = fxmlLoader.getController();
                itemPromotionController.setPromotionData(promotion);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML //ปุ่มสำหรับกดไปหน้าหนังสือทั้งหมด
    public void handleAllTypeBookButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pagesHeader() { // กำหนดและแสดงข้อมูลตรงส่วน head page
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

    @FXML // ปุ่มไปหน้าข้อมูลส่วนตัว
    public void handleToInformationButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("accountDetail", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML // คลิกที่รูป logo แล้วจะไปหน้า home
    public void mouseClickedInLogo(MouseEvent event){
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
