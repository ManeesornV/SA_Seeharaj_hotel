package ku.cs.shop.controllers.seller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ku.cs.shop.models.*;
import ku.cs.shop.services.PromotionDataSource;
import java.io.IOException;
import java.time.LocalDateTime;

public class createPromotionController {
    //in head
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;
    @FXML private Button status;
    @FXML private ImageView userImageView;
    @FXML private Label usernameInHead;
    // in create promotion
    @FXML private Label bookShopTopicLabel;
    @FXML private TextField codePromotionTextField;
    @FXML private TextField ratePromotionTextField;
    @FXML private TextField priceReductionInBahtTextField;
    @FXML private TextField priceReductionInPercentageTextField;
    @FXML private TextArea promotionDetailTextArea;
    @FXML private Label codePromotionWarning;
    @FXML private Label promotionWarningBeforeSaveData;
    @FXML private Label priceReductionInBahtWarning;
    @FXML private Label priceReductionInPercentageWarning;

    private AccountList accountList ;
    private Account account;
    private PromotionList promotionList;
    private PromotionDataSource promotionDataSource;

    public void initialize(){
        promotionDataSource = new PromotionDataSource("csv-data/promotion.csv");
        promotionList = promotionDataSource.readData();
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
        userImageView.setImage(new Image(account.getImagePath()));
        pagesHeader();
        bookShopTopicLabel.setText(account.getShopName());
    }

    public void sendPromotionDataToWrite(){
        Promotion promotion = new Promotion(
                account.getShopName(),
                codePromotionTextField.getText(),
                Integer.parseInt(ratePromotionTextField.getText()),
                Integer.parseInt(priceReductionInPercentageTextField.getText()),
                Integer.parseInt(priceReductionInBahtTextField.getText()),
                promotionDetailTextArea.getText(),
                LocalDateTime.now()
        );

        promotionList.addPromotion(promotion);
        promotionDataSource.writeData(promotionList);
    }

    @FXML //ทำงานเมื่อกรอก codePromotion
    public void handleKeyCodePromotion() {
        String codePromotion = codePromotionTextField.getText();
        codePromotionWarning.setText(Promotion.checkCodePromotionCondition(codePromotion));
        if (Promotion.getCodePromotionCheck()){
            if (promotionList.checkCodePromotionHaveUsed(codePromotion)) {
                codePromotionWarning.setText("โค้ดโปรโมชั่นนี้ถูกใช้งานไปแล้ว") ;
                codePromotionWarning.setTextFill(Color.rgb(210, 39, 30));
            } else {
                codePromotionWarning.setText("โค้ดโปรโมชั่นนี้สามารถใช้งานได้") ;
                codePromotionWarning.setTextFill(Color.rgb(21, 117, 84));
            }
        }
        else {
            codePromotionWarning.setTextFill(Color.rgb(210, 39, 30));
        }
    }

    @FXML //ทำงานเมื่อกรอกส่วนลดในหน่วยเปอร์เซ็นต์
    public void handleKeyPriceReductionInPercentage() {
        String priceReductionInPercentage = priceReductionInPercentageTextField.getText();
        priceReductionInPercentageWarning.setText(Promotion.checkPriceReductionInPercentageCondition(priceReductionInPercentage));
        if (Promotion.getPriceReductionInPercentageCheck()){
            priceReductionInPercentageWarning.setText("ส่วนลดตรงตามารูปแบบที่กำหนด") ;
            priceReductionInPercentageWarning.setTextFill(Color.rgb(21, 117, 84));
        }
        else {
            priceReductionInPercentageWarning.setTextFill(Color.rgb(210, 39, 30));
        }
    }

    @FXML //ทำงานเมื่อกรอกส่วนลดในหน่วยบาท
    public void handleKeyPriceReductionInBaht() {
        String priceReductionInBaht = priceReductionInBahtTextField.getText();
        priceReductionInBahtWarning.setText(Promotion.checkPriceReductionInBahtCondition(priceReductionInBaht));
        if (Promotion.getPriceReductionInBahtCheck()){
            priceReductionInBahtWarning.setText("ส่วนลดตรงตามารูปแบบที่กำหนด") ;
            priceReductionInBahtWarning.setTextFill(Color.rgb(21, 117, 84));
        }
        else {
            priceReductionInBahtWarning.setTextFill(Color.rgb(210, 39, 30));
        }
    }

    //เช็คการกรอกข้อมูลก่อนสมัคร
    public String checkData() {
        // ตรวจสอบว่าทุกช่องมีข้อมูล
        if ((codePromotionTextField.getText().equals("") || ratePromotionTextField.getText().equals("")
                || priceReductionInPercentageTextField.getText().equals("")
                || priceReductionInBahtTextField.getText().equals(""))
                || promotionDetailTextArea.getText().equals("")) {
            return "ข้อมูลไม่ครบถ้วน โปรดดูคำแนะนำและกรอกข้อมูลให้ครบ";
        }
        else {
            return " ";
        }
    }

    @FXML //บันทึกการแก้ไขโปรโมชั่น
    public void handleSavePromotionDataButton(ActionEvent actionEvent) {
        promotionWarningBeforeSaveData.setText(checkData());
        if(!(checkData().equals(" "))) {
            return;
        }
        sendPromotionDataToWrite();

        try {
            com.github.saacsos.FXRouter.goTo("promotion", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML // ยกเลิกการแก้ไขโปรโมชั่น
    public void handleCancelPromotionDataButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("promotion", accountList);
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
    public void handleAllTypeBookButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้าหนังสือทั้งหมด
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML //คลิกที่รูป logo แล้วจะไปหน้า home
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
}
