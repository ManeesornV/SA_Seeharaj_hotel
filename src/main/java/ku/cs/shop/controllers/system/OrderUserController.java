package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import ku.cs.shop.models.*;
import ku.cs.shop.services.OrderDataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderUserController implements Initializable {

    @FXML private ImageView logoJavaPai;
    @FXML private Button status;
    @FXML private Label usernameInHead;
    @FXML private ImageView img;
    @FXML private ImageView userImageView;
    @FXML private FlowPane orderListFlowPane;

    private ArrayList<Account> accountsList = new ArrayList<>();
    private String currentUser;
    private AccountList accountList;
    private Account account;
    private OrderDataSource orderDataSource;
    private OrderList orderList;
    private Order order = new Order();

    public void initialize (URL location, ResourceBundle resource) {
        orderDataSource = new OrderDataSource("csv-data/bookOrder.csv");
        orderList = orderDataSource.readData();

        accountList = (AccountList) com.github.saacsos.FXRouter.getData();
        account = accountList.getCurrentAccount();

        currentUser = account.getUserName();
        pagesHeader();

        userImageView.setImage(new Image(account.getImagePath()));
        orderByName();
    }

    // แสดงรายการสั่งซื้อของลูกค้า
    public void orderByName() {
        ArrayList<Order> orderByName = orderList.getOrderByCustomerName(currentUser);

        try {
            for (Order order : orderByName) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/orderUserItem.fxml"));

                orderListFlowPane.getChildren().add(fxmlLoader.load());
                OrderUserItemController orderUserItemController = fxmlLoader.getController();
                orderUserItemController.setData(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void handleToSellerButton(ActionEvent actionEvent) {
        if (account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")) {
            try {
                com.github.saacsos.FXRouter.goTo("sellerHaventApply", accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                com.github.saacsos.FXRouter.goTo("sellerStock", accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // แสดงหน้ารายการสั่งซื้อของลูกค้า
    @FXML
    public void handleToOrderPageButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("bookOrderOfUser", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // ปุ่มแสดงรายการสั่งซื้อที่จัดส่งแล้ว
    @FXML
    void handleCompletedOrderUserButton(ActionEvent event) {
        ArrayList<Order> orderByName = orderList.getOrderByCustomerName(currentUser);
        orderListFlowPane.getChildren().clear();
        for (Order order : orderByName) {
            if (!order.getTrackingNumber().equals("ยังไม่จัดส่ง")) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/orderUserItem.fxml"));

                    orderListFlowPane.getChildren().add(fxmlLoader.load());
                    OrderUserItemController orderUserItemController = fxmlLoader.getController();
                    orderUserItemController.setData(order);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ปุ่มแสดงรายการสั่งซื้อที่ยังไม่จัดส่ง
    @FXML
    void handleNewBookOrderButton(ActionEvent event) {
        ArrayList<Order> orderByName = orderList.getOrderByCustomerName(currentUser);
        orderListFlowPane.getChildren().clear();
        for (Order order : orderByName) {
            if (order.getTrackingNumber().equals("ยังไม่จัดส่ง")) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/orderUserItem.fxml"));

                    orderListFlowPane.getChildren().add(fxmlLoader.load());
                    OrderUserItemController orderUserItemController = fxmlLoader.getController();
                    orderUserItemController.setData(order);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ไปยังหน้า market page
    @FXML
    public void handleAllTypeBookButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("pageBookType", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleToAccountDetailButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("accountDetail", accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // กำหนดและแสดงข้อมูลตรงส่วน head page
    public void pagesHeader() {
        usernameInHead.setText(account.getUserName());
        img.setImage(new Image(account.getImagePath()));
        if (account instanceof AdminAccount) {
            status.setText("Admin");
        } else if (account.getShopName().equals("ยังไม่ได้สมัครเป็นผู้ขาย")) {
            status.setText("User");
        } else {
            status.setText("Seller");
        }
    }

    // Logo Javapai ไปยังหน้า Home
    @FXML
    public void mouseClickedInLogo(MouseEvent event){
        try{
            logoJavaPai.getOnMouseClicked();
            com.github.saacsos.FXRouter.goTo("home", accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ออกจากระบบกลับไปล็อกอิน
    @FXML
    public void handleToLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
