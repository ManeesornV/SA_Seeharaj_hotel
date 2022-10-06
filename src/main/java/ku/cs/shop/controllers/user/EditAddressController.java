package ku.cs.shop.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.services.AccountDataSource;

import java.io.IOException;

public class EditAddressController {
    private Account account;
    private AccountList accountList;
    private AccountDataSource accountDataSource;
    private String username;

    @FXML
    public void initialize(){
        accountDataSource = new AccountDataSource("csv-data/accountData.csv") ;
        accountList = accountDataSource.readData() ;
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;

        username = account.getUserName();
        editAddressTextField.setText(account.getAddress().replace("null", "เพิ่มรายละเอียดที่อยู่ที่นี้"));
    }

    @FXML private TextField editAddressTextField;

    @FXML
    public void handleGoToInformationPageWhenEditAddress(){ //ปุ่มกลับไปหน้าข้อมูลส่วนตัวหลังแก้ไขข้อมูลเสร็จ
        account.setAddress(editAddressTextField.getText());
        AccountDataSource accountDataSource = new AccountDataSource("csv-data/accountData.csv") ;
        accountDataSource.writeData(accountList);

        try{
            com.github.saacsos.FXRouter.goTo("accountDetail");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า detailUser ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleGotoInformationPageWhenCancelEditAddress(){ //ปุ่มกลับไปหน้าข้อมูลส่วนตัวเมื่อไม่ต้องการแก้ไขข้อมูลแล้ว
        try {
            com.github.saacsos.FXRouter.goTo("accountDetail");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า detailUser ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
