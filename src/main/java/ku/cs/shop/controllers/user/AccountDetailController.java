package ku.cs.shop.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.shop.models.*;

import java.io.IOException;

public class AccountDetailController<handleToOrderPageButton> {

    @FXML private Label birthdayLabel;
    @FXML private Label birthMonthLabel;
    @FXML private Label birthYearLabel;
    @FXML private Label passwordLabel;
    @FXML private Label sexLabel;
    @FXML private Label phoneLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label firstnameLabel;
    @FXML private Label usernameLabel;
    @FXML private ImageView userImageView ;
    @FXML private Label addressLabel ;
    @FXML private Button forAdminButton ;
    @FXML private Button status;
    @FXML private Label usernameInHead;
    @FXML private ImageView img;
    @FXML private ImageView logoJavaPai;

    private AccountList accountList;
    private Account account;

    public void initialize(){
        accountList = (AccountList) FXRouter.getData();
        account = accountList.getCurrentAccount();
        showData();
        pagesHeader();
        if (account instanceof AdminAccount) {
            forAdminButton.setStyle("-fx-background-color: #F99F28; ");
        }
    }

    public void showData(){ // โชว์ข้อมูลส่วนตัวของผู้ใช้ระบบ
        userImageView.setImage(new Image(account.getImagePath())) ;
        usernameLabel.setText(account.getUserName());
        firstnameLabel.setText(account.getFirstName());
        lastnameLabel.setText(account.getLastName());
        passwordLabel.setText(setPassword(account.getPassword().length()));
        birthdayLabel.setText(account.getBirthDay());
        birthMonthLabel.setText(account.getBirthMonth());
        birthYearLabel.setText(account.getBirthYear());
        sexLabel.setText(account.getSex().replace("null", "ยังไม่ได้เพิ่มข้อมูลเพศ"));
        phoneLabel.setText(account.getPhone().replace("null", "ยังไม่ได้เพิ่มข้อมูลเบอร์โทร"));
        addressLabel.setText(account.getAddress().replace("null", "ยังไม่ได้เพิ่มข้อมูลที่อยู่"));
    }

    public String setPassword(int passwordLength) {
        String password = "" ;
        int i = 0 ;
        while (i < passwordLength) {
            password += "*";
            i++ ;
        }
        return  password ;
    }

    @FXML
    public void handleAllTypeBookButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้าหนังสือทั้งหมด
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleToEditInformationPageButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้าแก้ไขข้อมูลส่วนตัว
        try {
            com.github.saacsos.FXRouter.goTo("editInformation", accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า editInformation ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
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
                System.err.println("ไปที่หน้า sellerStock ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    // ปุ่มกดไปหน้าแก้ไขข้อมูลที่อยู่
    @FXML
    public void handleToEditAddressButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("editAddress" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า editAddress ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleToForAdminButton(ActionEvent actionEvent) {
        try {
            if(!(account instanceof AdminAccount)) { return; }
            com.github.saacsos.FXRouter.goTo("forAdmin" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า ForAdmin ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
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
    public void mouseClickedInLogo(MouseEvent event){ //คลิกที่ logo จะไปหน้า home
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

    @FXML
    public void handleToReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("reporting" ,accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า report ไม่ได้");
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
}