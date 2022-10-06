package ku.cs.shop.controllers.user;

import javafx.animation.Timeline;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.UserAccount;
import ku.cs.shop.services.AccountDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LoginController {

    @FXML private TextField usernameTextField ;
    @FXML private TextField passwordField ;
    @FXML private Label errorLabel ;
    @FXML private GridPane notificationGrid ;

    private AccountList accountList ;
    private AccountDataSource accountDataSource ;

    @FXML
    public void initialize () {
        accountDataSource = new AccountDataSource(("csv-data/accountData.csv")) ;
        accountList = accountDataSource.readData() ;
    }

    @FXML
    public void handleToRegisterButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้า หนังสือทั้งหมด (เพจหลัก)
        try {
            com.github.saacsos.FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleToAboutUsButton(ActionEvent actionEvent) { //ปุ่มสำหรับกดไปหน้า about us
        try {
            com.github.saacsos.FXRouter.goTo("aboutUs");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า aboutUs ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {

        String userName = usernameTextField.getText() ;
        String password = passwordField.getText() ;
        Account account = accountList.login(userName, password) ;

        if (account != null) {
            errorLabel.setTextFill(Color.rgb(255, 255, 255));
            if(account instanceof UserAccount){
                UserAccount userAccount = (UserAccount) account ;

                if (userAccount.getStatus().equals("banned")) {
                    userAccount.setTryToLogin(userAccount.getTryToLogin()+1);
                    accountDataSource.writeData(accountList);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/loginNotification.fxml"));
                    try {
                        notificationGrid.add(fxmlLoader.load(), 0, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                else {
                    userAccount.setLoginTime(LocalDateTime.now(ZoneId.of("Asia/Bangkok")));
                    userAccount.setTryToLogin(0);
                    accountDataSource.writeData(accountList);
                }
            }
            goToHome();
        }
        else {
            errorLabel.setTextFill(Color.rgb(210, 39, 30));
        }


    }

    @FXML
    public void goToHome() {
        try {
            com.github.saacsos.FXRouter.goTo("home", accountList);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
