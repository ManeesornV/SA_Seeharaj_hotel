package ku.cs.shop.controllers.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.shop.controllers.user.UserListForAdminController;
import ku.cs.shop.models.Reporting;

public class FullImageController {

    @FXML private ImageView fullImageView ;

    private UserListForAdminController userListForAdminController;

    public void setUserListForAdminController(UserListForAdminController userListForAdminController) {
        this.userListForAdminController = userListForAdminController;
    }

    public void setFullImageView(Reporting reporting) {
        fullImageView.setImage(new Image(reporting.getImagePath()));
    }

    public void handleCloseFullImageButton(ActionEvent actionEvent) {
        userListForAdminController.handleCloseFullImageButton();
    }
}
