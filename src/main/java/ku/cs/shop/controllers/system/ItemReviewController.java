package ku.cs.shop.controllers.system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.shop.models.Reviews;
import ku.cs.shop.models.ReviewsList;
import ku.cs.shop.services.ReviewsDataSource;

public class ItemReviewController {
    @FXML private Label commentLabel;
    @FXML private ImageView userImageView;
    @FXML private Label usernameLabel;

    private Reviews reviews;
    private ReviewsDataSource reviewsDataSource = new ReviewsDataSource("csv-data/reviews.csv") ;
    private ReviewsList reviewsList = reviewsDataSource.readData();

    public void setReviewData(Reviews reviews) {
        this.reviews = reviews;
        changeData();
    }

    public void changeData() { // เปลี่ยนข้อมูล comment
        usernameLabel.setText(reviews.getUserName());
        userImageView.setImage(new Image(reviews.getImagePath()));
        commentLabel.setText(reviews.getComment());
    }
}