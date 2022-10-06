package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import ku.cs.shop.controllers.system.ItemPromotionController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.PromotionDataSource;
import com.github.saacsos.FXRouter;
import java.io.IOException;
import java.util.ArrayList;

public class PromotionController {
    //in head
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private Button status;
    @FXML private ImageView userImageView;
    @FXML private Label usernameInHead;
    // in promotion detail
    @FXML private FlowPane promotionFlowPane;

    private AccountList accountList ;
    private Account account;
    private PromotionList promotionList;
    private PromotionDataSource promotionDataSource;

    public void initialize(){
        promotionDataSource = new PromotionDataSource("csv-data/promotion.csv");
        promotionList = promotionDataSource.readData();
        accountList = (AccountList) FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
        userImageView.setImage(new Image(account.getImagePath()));
        pagesHeader();
        showPromotionByShopName(account.getShopName());
    }

    @FXML // กดไปหน้าส้รางโปรโมชั่น
    public void handleToCreatePromotionPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("createPromotion" , accountList);
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

    @FXML
    public void handleToOrderPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("bookOrderOfUser" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleToSellerButton(ActionEvent actionEvent) {
        if (true) {
            try {
                com.github.saacsos.FXRouter.goTo("sellerHaventApply",accountList);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า sellerHaventApply ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    public void mouseClickedInLogo(MouseEvent event){ //คลิกที่รูป logo แล้วจะไปหน้า home
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAllTypeBookButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้าหนังสือทั้งหมด
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPromotionByShopName(String shopName) { // แสดงโปรโมชั่น
        promotionFlowPane.getChildren().clear();
        ArrayList<Promotion> promotionByShopName = promotionList.getPromotionByShopName(shopName);
        try {
            for (Promotion promotion : promotionByShopName) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/itemPromotion.fxml"));

                promotionFlowPane.getChildren().add(fxmlLoader.load());
                ItemPromotionController itemPromotionController = fxmlLoader.getController();
                itemPromotionController.setPromotionData(promotion);
            }
        }
        catch (IOException e) {
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
}
