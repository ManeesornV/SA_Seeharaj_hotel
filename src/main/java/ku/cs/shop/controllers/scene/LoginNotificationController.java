package ku.cs.shop.controllers.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginNotificationController {

    @FXML //ปุ่มสำหรับกดไปหน้า about us
    public void handleToAboutUsButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("aboutUs");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า aboutUs ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
