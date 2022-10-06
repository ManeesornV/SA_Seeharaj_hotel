package ku.cs.shop.controllers.system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ku.cs.shop.models.*;
import ku.cs.shop.services.BookDetailDataSource;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;
import ku.cs.shop.services.OrderDataSource;
import ku.cs.shop.services.PromotionDataSource;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ConfirmOrderController {
    @FXML private Label bookNameLabel;
    @FXML private Label sumBookPriceLabel;
    @FXML private Text noficationItem;
    @FXML private TextField inputNumOfBookTextField;
    @FXML private TextField inputCodePromotion;
    @FXML private Label codePromotionWarningLabel;

    private Random random = new Random();
    private Order order = new Order();

    private Book book;
    private BookList bookList;
    private PromotionList promotionList;
    private PromotionDataSource promotionDataSource;
    private ArrayList<Object> objectForPassing = new ArrayList<>();

    private BookDetailController bookDetailController;
    private int totalBookOrdered;
    private int stockInShop;
    private double costOfBook;
    private double totalBookOrderedWhenUseCodePromotion;
    private ArrayList<Promotion> shopPromotion;
    private OrderDataSource orderDataSource;
    private OrderList orderList;

    public void setController(BookDetailController book) {
        orderDataSource = new OrderDataSource("csv-data/bookOrder.csv");
        orderList = orderDataSource.readData();

        this.bookDetailController = book;
        setBookListAndBook();

        promotionDataSource = new PromotionDataSource("csv-data/promotion.csv");
        promotionList = promotionDataSource.readData();
        shopPromotion = promotionList.getPromotionByShopName(bookDetailController.getBook().getBookShop());

        bookNameLabel.setText(bookDetailController.getBook().getBookName());
        setCostOfBook(bookDetailController.getBook().getBookPrice());
        setStockInShop(bookDetailController.getBook().getBookStock());
    }

    public void setBookListAndBook() {
        this.bookList = bookDetailController.getBookList();
        this.book = bookDetailController.getBook();
    }

    public void setStockInShop(int stockInShop) { this.stockInShop = stockInShop; }
    public void setCostOfBook(double costOfBook) { this.costOfBook = costOfBook; }
    public void setTotalBookOrdered(int totalBookOrdered) { this.totalBookOrdered = totalBookOrdered; }

    // ตรวจสอบการรับข้อมูลจำนวนสั่งหนังสือจาก User
    public int checkInputNumOfOrder() {
        if (Pattern.matches("[1-9]+[0-9]+" , inputNumOfBookTextField.getText())
                || ( (inputNumOfBookTextField.getText().length() == 1) && Pattern.matches("[0-9]+", inputNumOfBookTextField.getText()) )
                && Integer.parseInt(inputNumOfBookTextField.getText()) >= 0) {
            setTotalBookOrdered(Integer.parseInt(inputNumOfBookTextField.getText()));
        }

        if (Integer.parseInt(inputNumOfBookTextField.getText()) < 0)
            setTotalBookOrdered(Integer.parseInt("0"));
        return 0;
    }

    // ครวจสอบการสั่งหนังสือกับสินค้าในร้านค้า
    @FXML
    void handleAddNumOfBookInput(ActionEvent event) {
        checkInputNumOfOrder();
        if(totalBookOrdered > stockInShop)
            noficationItem.setText("สินค้าในคลังไม่เพียงพอ กรุณากรอกจำนวนใหม่");
            sumBookPriceLabel.setText("0.00");

        if (totalBookOrdered <= stockInShop) {
            noficationItem.setText("");
            sumBookPriceLabel.setText(String.format("%.02f",costOfBook * totalBookOrdered));
             if ( sumBookPriceLabel.getText().equals("0.00")) {
                 noficationItem.setText("กรุณากรอกจำนวนสินค้าให้ถูกต้อง (มากกว่า 0)");
             }
        }

        if (book.getBookStock() == 0)
            noficationItem.setText("    " +
                    "สินค้าในคลังหมดแล้ว กรุณาเลือกสินค้าใหม่");
    }

    @FXML
    void handleAddCodePromotionInput(ActionEvent event) {
        sumBookPriceLabel.setText(String.format("%.02f", checkUsePromotion()));
    }

    public double checkUsePromotion(){
        double sum = costOfBook * totalBookOrdered;
        totalBookOrderedWhenUseCodePromotion = promotionList.usePromotion(shopPromotion, inputCodePromotion.getText(), sum);
        return totalBookOrderedWhenUseCodePromotion;
    }

    //ทำงานเมื่อกรอก codePromotion
    @FXML
    public void handleKeyCodePromotion() {
        String shopName = bookDetailController.getBook().getBookShop();
        String codePromotion = inputCodePromotion.getText();
        codePromotionWarningLabel.setText(Promotion.checkCodePromotionCondition(codePromotion));
        if (Promotion.getCodePromotionCheck()){
            if (promotionList.checkCodePromotionIsCorrect(shopName,codePromotion)) {
                codePromotionWarningLabel.setText("โค้ดโปรโมชั่นนี้สามารถใช้งานได้") ;
                codePromotionWarningLabel.setTextFill(Color.rgb(21, 117, 84));
            } else {
                codePromotionWarningLabel.setText("โค้ดโปรโมชั่นนี้ไม่ตรงกับร้านค้า") ;
                codePromotionWarningLabel.setTextFill(Color.rgb(210, 39, 30));
            }
        }
        else {
            codePromotionWarningLabel.setTextFill(Color.rgb(210, 39, 30));
        }
    }

    // เพิ่มข้อมูลไปยัง bookOrder.csv
    @FXML
    public void handleConfirmBuyBook(ActionEvent event) {
        if (Double.parseDouble(sumBookPriceLabel.getText()) > 0 && bookDetailController.getBook().getBookStock() > 0) {
            order.setBookImage(bookDetailController.getBook().getBookImg());
            order.setBookName(bookDetailController.getBook().getBookName());
            order.setBookShop(bookDetailController.getBook().getBookShop());
            order.setTotalBookOrdered(Integer.parseInt(inputNumOfBookTextField.getText()));
            order.setTotalPriceOrdered(Double.parseDouble(sumBookPriceLabel.getText()));
            order.setTrackingNumber("ยังไม่จัดส่ง");
            order.setCustomerName(bookDetailController.getAccount().getUserName());
            order.setCustomerPhone(bookDetailController.getAccount().getPhone());
            order.setTimeOfOrdered(LocalDateTime.now());

            if (order.getCustomerPhone().equals("null"))
                order.setCustomerPhone("ไม่มีข้อมูลการติดต่อ");

            noficationItem.setText("               "
                    + "คุณซื้อหนังสือเล่มนี้สำเร็จ");

            orderList.addOrder(order);
            orderDataSource.writeData(orderList);
            book.setBookStock(stockInShop - totalBookOrdered);

            BookDetailDataSource bookDetailDataSource = new BookDetailDataSource("csv-data/bookDetail.csv");
            bookDetailDataSource.writeData(bookList);
        }
    }

    @FXML
    public void handleClosePage(ActionEvent event) {
      bookDetailController.handleClosePage();
    }
}
