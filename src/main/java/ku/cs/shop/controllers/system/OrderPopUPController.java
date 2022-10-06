package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.Order;
import ku.cs.shop.models.OrderList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.OrderDataSource;

import java.io.IOException;

public class OrderPopUPController {
    @FXML private TextField trackingNumberTextField;
    @FXML private Button shippedButton;
    @FXML private Label bookNamePopUpLabel;
    @FXML private Label customerNamePopUpLabel;

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
        bookNamePopUpLabel.setText(this.order.getBookName());
        customerNamePopUpLabel.setText(this.order.getCustomerName());

        DataSource<OrderList> dataSource= new OrderDataSource("csv-data/bookOrder.csv");
        dataSource.writeData(orders);
    }

    // กดปุ่มเพื่อเพิ่มข้อมูลเลขพัสดุ
    @FXML
    public void handleShippedButton(ActionEvent actionEvent){
        order.setTrackingNumber(trackingNumberTextField.getText());
        changeData();
        try {
            com.github.saacsos.FXRouter.goTo("orderList", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // กดเพื่อปิดหน้าต่าง pop up
    @FXML
    public void handleBackToOrderListButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("orderList", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
