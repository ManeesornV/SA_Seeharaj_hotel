package ku.cs.shop.controllers.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AboutUsController {

    @FXML private GridPane gridHeadChoice ;

    public void initialize () {
        try {
            FXMLLoader fxmlLoaderHead = new FXMLLoader();
            fxmlLoaderHead.setLocation(getClass().getResource("/ku/cs/aboutUsChoice.fxml"));
            gridHeadChoice.add(fxmlLoaderHead.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("โหลด Choice กลุ่มผู้พัฒนาไม่ได้ ตรวจสอบ Path");
        }
    }

    @FXML
    public void handleAboutUsButton (ActionEvent actionEvent) {
        try {
            gridHeadChoice.getChildren().remove(0) ;
            FXMLLoader fxmlLoaderHead = new FXMLLoader();
            fxmlLoaderHead.setLocation(getClass().getResource("/ku/cs/aboutUsChoice.fxml"));
            gridHeadChoice.add(fxmlLoaderHead.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("โหลด Choice กลุ่มผู้พัฒนาไม่ได้ ตรวจสอบ Path");
        }
    }

    @FXML
    public void handleAdminInfoButton (ActionEvent actionEvent) {
        try {
            gridHeadChoice.getChildren().remove(0) ;
            FXMLLoader fxmlLoaderHead = new FXMLLoader();
            fxmlLoaderHead.setLocation(getClass().getResource("/ku/cs/adminInfoChoice.fxml"));
            gridHeadChoice.add(fxmlLoaderHead.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("โหลด Choice ข้อมูลผู้พัฒนาไม่ได้ ตรวจสอบ Path");
        }
    }

    @FXML
    public  void handleBackToHome () {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
