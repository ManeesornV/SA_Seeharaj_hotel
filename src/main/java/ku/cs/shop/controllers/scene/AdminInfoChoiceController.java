package ku.cs.shop.controllers.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AdminInfoChoiceController {

    @FXML private GridPane gridInfo ;

    @FXML
    public void initialize () {
        try {
            FXMLLoader fxmlLoaderInfo = new FXMLLoader();
            fxmlLoaderInfo.setLocation(getClass().getResource("/ku/cs/subscene/fresh.fxml"));
            gridInfo.add(fxmlLoaderInfo.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChoice1Click(){
        try {
            gridInfo.getChildren().remove(0) ;
            FXMLLoader fxmlLoaderInfo = new FXMLLoader();
            fxmlLoaderInfo.setLocation(getClass().getResource("/ku/cs/subscene/fresh.fxml"));
            gridInfo.add(fxmlLoaderInfo.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChoice2Click(){
        try {
            gridInfo.getChildren().remove(0) ;
            FXMLLoader fxmlLoaderInfo = new FXMLLoader();
            fxmlLoaderInfo.setLocation(getClass().getResource("/ku/cs/subscene/tam.fxml"));
            gridInfo.add(fxmlLoaderInfo.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChoice3Click(){
        try {
            gridInfo.getChildren().remove(0) ;
            FXMLLoader fxmlLoaderInfo = new FXMLLoader();
            fxmlLoaderInfo.setLocation(getClass().getResource("/ku/cs/subscene/kwan.fxml"));
            gridInfo.add(fxmlLoaderInfo.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChoice4Click(){
        try {
            gridInfo.getChildren().remove(0) ;
            FXMLLoader fxmlLoaderInfo = new FXMLLoader();
            fxmlLoaderInfo.setLocation(getClass().getResource("/ku/cs/subscene/frince.fxml"));
            gridInfo.add(fxmlLoaderInfo.load(), 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
