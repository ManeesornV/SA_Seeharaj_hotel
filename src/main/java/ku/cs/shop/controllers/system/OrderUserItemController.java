package ku.cs.shop.controllers.system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.shop.models.Order;

public class OrderUserItemController {

    @FXML private Label bookNameLabel;
    @FXML private Label totalBookOrderedLabel;
    @FXML private Label trakingNumberLabel;
    @FXML private Label bookShopLabel;
    @FXML private Label totalPriceLabel;
    @FXML private ImageView bookImg;

    private Order order;

    public void setData(Order order) {
        this.order = order;
        changeData();
    }

    public void changeData() {
        bookNameLabel.setText(order.getBookName());
        totalBookOrderedLabel.setText(order.getTotalBookOrdered() + " เล่ม");
        trakingNumberLabel.setText(order.getTrackingNumber());
        bookShopLabel.setText(order.getBookShop());
        totalPriceLabel.setText(String.format("%.02f", order.getTotalPriceOrdered()) + " Baht.");
        bookImg.setImage(new Image(order.getPicturePath()));
    }
}
