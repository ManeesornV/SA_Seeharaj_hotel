package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.shop.models.*;

import java.util.ArrayList;

public class ChoiceApplySubtypeBookController {
    @FXML private Label subTypeBookLabel;
    @FXML private TextField subTypeBookTextField;
    @FXML private Button addSubTypeBookButton;
    @FXML private Label notificationAddButton;

    private ProvideTypeBook provideTypeBook ;
    private ProvideTypeBook provideTypeBookData;
    private ProvideTypeBookList typeBookList;
    private ArrayList<ProvideTypeBook> typeBookArrayList;
    private AccountList accountList;
    private int index;
    private boolean checkAddButton = false;

    // กำหนดข้อมูลที่ได้รับมา
    public void setData(ProvideTypeBook provideTypeBook,ArrayList<ProvideTypeBook> typeBookArrayList, AccountList accountList,int index,ProvideTypeBookList typeBookList){
        this.provideTypeBook = provideTypeBook;
        this.typeBookArrayList = typeBookArrayList;
        this.accountList = accountList;
        this.index = index;
        this.typeBookList = typeBookList;
    }

    // เปลี่ยนข้อมูลตามข้อมูลที่ได้รับมา
    public void changeData() {
        subTypeBookLabel.setText(provideTypeBook.getSubTypeBook());
    }
    public void changeTextFieldData() {
        subTypeBookTextField.setText(typeBookArrayList.get(index).getSubTypeBook());
    }

    // ตรวจสอบการกดปุ่มเพิ่มข้อมูล
    public void handleKeySubTypeBookTextField(){
        if (!checkAddButton)
            notificationAddButton.setText("กรุณากด add ข้อมูล");
        else
            notificationAddButton.setText("");
    }

    // กดปุ่มเพิ่มข้อมูลและตรวจสอบข้อมูล
    @FXML
    public void handleAddSubTypeButton(ActionEvent actionEvent) {
        provideTypeBookData = new ProvideTypeBook();
        provideTypeBookData.setSubTypeBook(subTypeBookTextField.getText());
        provideTypeBookData.setSuperTypeBook(provideTypeBook.getSuperTypeBook());
        if (typeBookArrayList.size() != typeBookList.numOfSubTypeBook(provideTypeBook.getSuperTypeBook())){
            if(index != 0 && typeBookArrayList.size()-1 != index)
                typeBookArrayList.add(new ProvideTypeBook(provideTypeBook.getSuperTypeBook(),"ไม่มีรายละเอียด"));
            typeBookArrayList.add(provideTypeBookData);
        }
        else {
            typeBookArrayList.set(index,provideTypeBookData);
        }
        checkAddButton = true;
        notificationAddButton.setText("");
    }
}
