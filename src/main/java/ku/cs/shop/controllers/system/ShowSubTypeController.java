package ku.cs.shop.controllers.system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.shop.models.Book;
import ku.cs.shop.models.ProvideTypeBook;

import java.util.ArrayList;

public class ShowSubTypeController {

    @FXML private Label bookSubType;
    @FXML private Label bookTypeMoreInfo;

    public void changeData(ArrayList<ProvideTypeBook> provideTypeBook,Book book, int index) {
        bookSubType.setText(book.getTypeBookArrayList().get(index).getSubTypeBook());
        bookTypeMoreInfo.setText(provideTypeBook.get(index).getSubTypeBook());
    }
}
