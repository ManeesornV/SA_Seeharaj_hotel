package ku.cs.shop.controllers.user;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ku.cs.shop.controllers.scene.FullImageController;
import ku.cs.shop.controllers.system.ItemController;
import ku.cs.shop.controllers.system.ReportingInformationController;
import ku.cs.shop.models.*;
import ku.cs.shop.services.AccountDataSource;
import ku.cs.shop.services.ReportingDataSource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserListForAdminController {

    @FXML private ImageView accountImage ;
    @FXML private Label userNameLabel ;
    @FXML private Label firstNameLabel ;
    @FXML private Label lastNameLabel ;
    @FXML private Label birthDayLabel ;
    @FXML private Label birthMonthLabel ;
    @FXML private Label birthYearLabel ;
    @FXML private Label sexLabel ;
    @FXML private Label phoneLabel ;
    @FXML private Label shopNameLabel ;
    @FXML private Label addressLabel ;
    @FXML private Label loginTimeLabel ;
    @FXML private Label accountStatusLabel ;
    @FXML private Label tryToLoginLabel ;
    @FXML private Text tryToLoginText ;
    @FXML private ListView<UserAccount> userAccountListView ;
    @FXML private ImageView logoJavaPai;
    @FXML private Button provideUserButton;
    @FXML private ListView<Reporting> reportingListView ;
    @FXML private GridPane reportGrid ;
    @FXML private GridPane fullImageGrid ;

    private AccountList accountList ;
    private Account account ;
    private UserAccount selectedAccount ;
    private Reporting selectedReporting ;
    private ReportingList reportingList;
    private ReportingDataSource reportingDataSource ;
    private AccountDataSource accountDataSource ;
    private int countReportClick = 0 ;

    public void initialize(){
        accountList = (AccountList) com.github.saacsos.FXRouter.getData() ;
        account = accountList.getCurrentAccount() ;
        reportingDataSource = new ReportingDataSource("csv-data/report.csv") ;
        reportingList = reportingDataSource.readData() ;
        showUserAccountListView();
        clearSelectedUserAccount();
        handleSelectedUserAccountListView();
        handleSelectedReportingListView();

        accountDataSource = new AccountDataSource("csv-data/accountData.csv") ;
    }

    //User Zone
    //เพิ่มรายชื่อ UserAccount ใน ListView แล้วโชว์
    private void showUserAccountListView() {
        userAccountListView.getItems().addAll(accountList.getUserAccounts());
        userAccountListView.refresh();
    }

    //เลือก UserAccount จากการคลิกใน ListView
    private void handleSelectedUserAccountListView() {
        userAccountListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<UserAccount>() {
                    @Override
                    public void changed(ObservableValue<? extends UserAccount> observable,
                                        UserAccount oldValue, UserAccount newValue) {
                        showSelectedUserAccount(newValue);
                        showReportingListView(newValue);
                        selectedAccount = newValue ;
                    }
                });
    }

    //แสดงรายละเอียด Account ที่เลือก
    private void showSelectedUserAccount(UserAccount userAccount) {
        accountImage.setImage(new Image(userAccount.getImagePath()));
        userNameLabel.setText(userAccount.getUserName());
        firstNameLabel.setText(userAccount.getFirstName());
        lastNameLabel.setText(userAccount.getLastName());
        birthDayLabel.setText(userAccount.getBirthDay());
        birthMonthLabel.setText(userAccount.getBirthMonth());
        birthYearLabel.setText(userAccount.getBirthYear());
        sexLabel.setText(userAccount.getSex().replace("null", "ยังไม่ได้เพิ่มข้อมูลเพศ"));
        phoneLabel.setText(userAccount.getPhone().replace("null", "ยังไม่ได้เพิ่มข้อมูลเบอร์โทร"));
        shopNameLabel.setText(userAccount.getShopName());
        addressLabel.setText(userAccount.getAddress().replace("null", "ยังไม่ได้เพิ่มข้อมูลที่อยู่"));
        loginTimeLabel.setText(getStringTime(userAccount));
        accountStatusLabel.setText(userAccount.getStatus());
        accountStatusLabel.setTextFill(Color.rgb(21, 117, 84));
        tryToLoginText.setText("");
        tryToLoginLabel.setText("");

        //แสดงจำนวนครั้งที่พยายามเข้าสู่ระบบหากบัญชีถูกระงับ
        if (userAccount.getStatus().equals("banned")) {
            accountStatusLabel.setTextFill(Color.rgb(210, 39, 30));
            tryToLoginText.setText("พยายามเข้าสู่ระบบ : ");
            tryToLoginLabel.setText(userAccount.getTryToLogin() + " ครั้ง");
        }
    }

    //set Time Format
    private String getStringTime(UserAccount userAccount) {
        LocalDateTime loginTime = userAccount.getLoginTime();
        return "วันที่ " + loginTime.getDayOfMonth() + " " + convertToThaiMonth(loginTime.getMonth().toString()) + " " + loginTime.getYear()
                + "  เวลา " + loginTime.getHour() + ":" + loginTime.getMinute() + ":" + loginTime.getSecond() ;
    }

    private String convertToThaiMonth(String month) {
        if (month.equals("JANUARY")) { month = "มกราคม" ; }
        else if (month.equals("FEBRUARY")) { month = "กุมภาพันธ์" ; }
        else if (month.equals("MARCH")) { month = "มีนาคม" ; }
        else if (month.equals("APRIL")) { month = "เมษายน" ; }
        else if (month.equals("MAY")) { month = "พฤษภาคม" ; }
        else if (month.equals("JUNE")) { month = "มิถุนายน" ; }
        else if (month.equals("JULY")) { month = "กรกฎาคม" ; }
        else if (month.equals("AUGUST")) { month = "สิงหาคม" ; }
        else if (month.equals("SEPTEMBER")) { month = "กันยายน" ; }
        else if (month.equals("OCTOBER")) { month = "ตุลาคม" ; }
        else if (month.equals("NOVEMBER")) { month = "พฤศจิกายน" ; }
        else if (month.equals("DECEMBER")) { month = "ธันวาคม" ; }
        return month ;
    }

    private void clearSelectedUserAccount() {
        accountImage.setImage(new Image(new File(System.getProperty("user.dir")
                + File.separator
                + "account-images"
                + File.separator
                + "default.png").toURI().toString()));
        userNameLabel.setText("ชื่อผู้ใช้งาน : ยังไม่ระบุ");
        firstNameLabel.setText("ชื่อ : ยังไม่ระบุ");
        lastNameLabel.setText("นามสกุล : ยังไม่ระบุ");
        birthDayLabel.setText("ยังไม่ระบุ");
        birthMonthLabel.setText("");
        birthYearLabel.setText("");
        sexLabel.setText("ยังไม่ระบุ");
        phoneLabel.setText("ยังไม่ระบุ");
        shopNameLabel.setText("ยังไม่ระบุ");
        addressLabel.setText("ยังไม่ระบุ");
        loginTimeLabel.setText("ยังไม่ระบุ");
        accountStatusLabel.setText("ยังไม่ระบุ");
        tryToLoginText.setText("พยายามเข้าสู่ระบบ : ");
        tryToLoginLabel.setText("ยังไม่ระบุ");
    }


    //Report Zone
    //เพิ่มรายชื่อ Reporting ของ Account ใน ListView แล้วโชว์
    private void showReportingListView(UserAccount userAccount) {
        reportingListView.getItems().removeAll(reportingList.getCurrantReportedAccount()) ;
        reportingList.setCurrantReportedAccount(userAccount.getUserName());
        reportingListView.getItems().addAll(reportingList.getCurrantReportedAccount()) ;
        reportingListView.refresh();
    }

    //เลือก Reporting จากการคลิกใน ListView
    private void handleSelectedReportingListView() {
        reportingListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Reporting>() {
                    @Override
                    public void changed(ObservableValue<? extends Reporting> observable,
                                        Reporting oldValue, Reporting newValue) {
                        setReportingFXML(newValue);
                        selectedReporting = newValue ;

                    }
                });
    }

    private void setReportingFXML(Reporting reporting) {
        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ku/cs/subscene/reportInformation.fxml"));
        ReportingInformationController reportingInformationController ;
        if (countReportClick == 0 && reporting != null) {
            try {
                reportGrid.add(fxmlLoader.load(), 0, 0);
                reportingInformationController = fxmlLoader.getController();
                reportingInformationController.setData(reporting);
                reportingInformationController.setUserListForAdminController(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            countReportClick++ ;
            return;
        }
        if (reporting != null) {
            try {
                reportGrid.getChildren().remove(0);
                reportGrid.add(fxmlLoader.load(), 0, 0);
                reportingInformationController = fxmlLoader.getController();
                reportingInformationController.setData(reporting);
                reportingInformationController.setUserListForAdminController(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleCancelButton() {
        reportGrid.getChildren().remove(0);
        countReportClick = 0 ;
    }

    public void showFullImage() {
        try {
            FXMLLoader fxmlLoaderForFullImage= new FXMLLoader();
            fxmlLoaderForFullImage.setLocation(getClass().getResource("/ku/cs/subscene/fullImage.fxml"));
            fullImageGrid.add(fxmlLoaderForFullImage.load(),0,0);
            FullImageController fullImageController = fxmlLoaderForFullImage.getController() ;
            fullImageController.setFullImageView(selectedReporting);
            fullImageController.setUserListForAdminController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCloseFullImageButton() {
        fullImageGrid.getChildren().remove(0);
    }

    @FXML
    public void handleBanButton(ActionEvent actionEvent) {
        selectedAccount.setStatus("banned");
        accountStatusLabel.setText("banned");
        accountStatusLabel.setTextFill(Color.rgb(210, 39, 30));
    }

    @FXML
    public void handleUnbanButton(ActionEvent actionEvent) {
        selectedAccount.setStatus("working");
        accountStatusLabel.setText("working");
        accountStatusLabel.setTextFill(Color.rgb(21, 117, 84));
    }

    @FXML
    public void handleToInformationButton(ActionEvent actionEvent) {
        try {
            accountDataSource.writeData(accountList);
            com.github.saacsos.FXRouter.goTo("accountDetail", accountList);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า detailUser ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML
    public void mouseClickedInLogo(MouseEvent event){
        try{
            logoJavaPai.getOnMouseClicked();
            accountDataSource.writeData(accountList);
            com.github.saacsos.FXRouter.goTo("home" ,accountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleToProvideShopButton(ActionEvent actionEvent) {
        try {
            accountDataSource.writeData(accountList);
            com.github.saacsos.FXRouter.goTo("provideTypeBookByAdmin" ,accountList);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า provideTypeBookByAdmin ไม่ได้");
            e.printStackTrace();
        }
    }

}
