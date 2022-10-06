package ku.cs.shop.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ku.cs.shop.models.ProvideTypeBook;
import ku.cs.shop.models.ProvideTypeBookList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.ProvideTypeBookDataSource;

import java.io.IOException;

public class ChoiceProvideTypeBookController {
    @FXML private Label numSubTypeBookLabel;
    @FXML private TextField subTypeBookTextField;
    @FXML private Button enterTypeBookButton;
    @FXML private Label notificationSubTypeBookLabel;

    private ProvideTypeBook provideTypeBook;
    private ProvideTypeBookList provideTypeBookList;
    private boolean checkSubTypeBookButton = false;

    // กำหนดข้อมูลที่ได้รับมา
    public void setData(ProvideTypeBook provideTypeBook, ProvideTypeBookList provideTypeBookList, int numSubtype){
        this.provideTypeBook = provideTypeBook;
        this.provideTypeBookList = provideTypeBookList;
        numSubTypeBookLabel.setText(String.valueOf(numSubtype));
    }

    // แจ้งเตือนการกดปุ่ม enter ของข้อมูล
    @FXML
    public void handleKeySubtypeBookTextField(){
        if(!checkSubTypeBookButton){
            notificationSubTypeBookLabel.setText("กรุณากด Enter เพื่อเพิ่มข้อมูล");
        }
        else {
            notificationSubTypeBookLabel.setText("");
        }
    }

    // กดปุ่มเพื่อตรวจสอบข้อมูล subTypeBook ที่รับเข้ามา
    @FXML
    public void handleEnterSubTypeBookButton(ActionEvent actionEvent){
        this.provideTypeBook.setSubTypeBook(subTypeBookTextField.getText());
        checkSubTypeBookButton = true;
        notificationSubTypeBookLabel.setText("");

        DataSource<ProvideTypeBookList> dataSource;
        dataSource = new ProvideTypeBookDataSource("csv-data/provideTypeBookData.csv");
        ProvideTypeBookList provideTypeBookListData = dataSource.readData();
        provideTypeBookListData.addTypeBook(this.provideTypeBook);

        dataSource.writeData(provideTypeBookListData);
    }

}
