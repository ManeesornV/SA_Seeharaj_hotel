package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.OrderDataSource;

import java.io.IOException;

public class OrderController {
    @FXML private ImageView bookImageView;
    @FXML private Label bookNameLabel;
    @FXML private Label numBookLabel;
    @FXML private Label notificationForStock;
    @FXML private Label nameCustomerLabel;
    @FXML private Label contactCustomerLabel;
    @FXML private Label numPriceLabel;
    @FXML private Button editStatusButton;
    @FXML private Label statusBookLabel;
    @FXML private GridPane gridPaneForPopup;

    private Order order;
    private OrderList orders;
    private AccountList accountList ;
    private Account account ;

    // กำหนดข้อมูลที่ได้รับมา
    public void setData(Order order,AccountList accountList,OrderList orders) {
        this.order = order;
        this.accountList = accountList;
        this.orders = orders;
    }

    // เปลี่ยนข้อมูลตามข้อมูลที่ได้รับมา
    public void changeData() {
        bookNameLabel.setText(order.getBookName());
        numPriceLabel.setText(order.getTotalPriceOrdered() + "");
        numBookLabel.setText(order.getTotalBookOrdered() + "");
        nameCustomerLabel.setText(order.getCustomerName());
        contactCustomerLabel.setText(order.getCustomerPhone());
        statusBookLabel.setText(order.getTrackingNumber());
        bookImageView.setImage(new Image(order.getPicturePath()));
    }

    // กดปุ่มแก้ไขข้อมูลการจัดส่ง
    @FXML
    public void handleEditOrderButton(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ku/cs/shippedOrderPopUp.fxml"));

            gridPaneForPopup.add(fxmlLoader.load(), 0, 0);
            OrderPopUPController orderPopUPController = fxmlLoader.getController();
            orderPopUPController.setData(this.order, accountList, this.orders);
            orderPopUPController.changeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
