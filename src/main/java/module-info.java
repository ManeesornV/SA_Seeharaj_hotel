module ku.cs {
    requires javafx.controls;
    requires opencsv;
    requires javafx.fxml;
    requires org.apache.commons.lang3;

    opens ku.cs to javafx.fxml;
    exports ku.cs;

//    exports ku.cs.shop.controllers;
//    opens ku.cs.shop.controllers to javafx.fxml;
    exports ku.cs.shop.services;
    opens ku.cs.shop.services to javafx.fxml;
    exports ku.cs.shop.controllers.seller;
    opens ku.cs.shop.controllers.seller to javafx.fxml;
    exports ku.cs.shop.controllers.scene;
    opens ku.cs.shop.controllers.scene to javafx.fxml;
    exports ku.cs.shop.controllers.system;
    opens ku.cs.shop.controllers.system to javafx.fxml;
    exports ku.cs.shop.controllers.user;
    opens ku.cs.shop.controllers.user to javafx.fxml;
}