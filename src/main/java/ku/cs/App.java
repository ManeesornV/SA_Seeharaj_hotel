package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.github.saacsos.FXRouter;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application
{
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "JAVAPAI",1024.0,768.0);
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("bookDetail", packageStr + "bookDetail.fxml");
        FXRouter.when("home",packageStr + "home.fxml");
        FXRouter.when("basket", packageStr + "basket.fxml");
        FXRouter.when("login",packageStr + "login.fxml");
        FXRouter.when("register" , packageStr + "register.fxml");
        FXRouter.when("applyBook", packageStr + "applyBook.fxml");
        FXRouter.when("sellerStock", packageStr + "sellerStock.fxml");
        FXRouter.when("applyToBeASeller", packageStr + "applyToBeASeller.fxml");
        FXRouter.when("item",packageStr + "item.fxml");
        FXRouter.when("aboutUs",packageStr + "aboutUs.fxml");
        FXRouter.when("aboutUsInformation",packageStr + "aboutUsInformation.fxml");
        FXRouter.when("editInformation",packageStr + "editInformation.fxml");
        FXRouter.when("pageBookType",packageStr + "pageBookType.fxml");
        FXRouter.when("sellerHaventApply",packageStr + "sellerHaventApply.fxml");
        FXRouter.when("orderList",packageStr + "orderList.fxml");
        FXRouter.when("editStock",packageStr + "editStock.fxml");
        FXRouter.when("accountDetail",packageStr + "accountDetail.fxml");
        FXRouter.when("pageBookShop",packageStr + "pageBookShop.fxml");
        FXRouter.when("forAdmin",packageStr + "userListForAdmin.fxml");
        FXRouter.when("editAddress",packageStr + "editAddress.fxml");
        FXRouter.when("itemReview",packageStr + "itemReview.fxml");
        FXRouter.when("provideTypeBookByAdmin",packageStr + "provideTypeBookByAdmin.fxml");
        FXRouter.when("reporting",packageStr + "reporting.fxml");
        FXRouter.when("bookOrderOfUser", packageStr + "bookOrderOfUser.fxml");
        FXRouter.when("orderUserItem", packageStr + "orderUserItem.fxml");
        FXRouter.when("reportingInBookDetail", packageStr + "reportingInBookDetail.fxml");
        FXRouter.when("createPromotion", packageStr + "createPromotion.fxml");
        FXRouter.when("promotion", packageStr + "promotion.fxml");
        FXRouter.when("itemPromotion", packageStr + "itemPromotion.fxml");
        FXRouter.when("pagePromotionByBookShop", packageStr + "pagePromotionByBookShop.fxml");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}