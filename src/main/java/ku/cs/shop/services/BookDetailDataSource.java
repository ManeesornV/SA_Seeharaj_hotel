package ku.cs.shop.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import ku.cs.shop.models.Book;
import ku.cs.shop.models.BookList;
import ku.cs.shop.models.ProvideTypeBook;
import ku.cs.shop.models.ProvideTypeBookList;

public class BookDetailDataSource implements DataSource<BookList> {

    private String filename;
    public BookDetailDataSource(){}
    public BookDetailDataSource(String filename) { this.filename = filename; }

    public BookList readData()
    {
        BookList bookList = new BookList();

        try {
            FileReader file = new FileReader(filename, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(file);
            String[] data = null;

            while ((data = reader.readNext()) != null) {
                String bookName = data[0].trim();
                String bookShop = data[1].trim();
                String bookAuthor = data[2].trim();
                String bookISBN = data[3].trim();
                String bookType = data[4].trim();
                String bookDetail = data[5].trim();
                String bookPublisher = data[6].trim();
                String bookImg = data[7].trim();
                int bookStock = Integer.parseInt(data[8].trim());
                String bookPage = data[9].trim();
                int leastStock = Integer.parseInt(data[10].trim());
                double bookPrice = Double.parseDouble(data[11].trim());
                LocalDateTime localDateTime = LocalDateTime.parse(data[12].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                //loop max subtype
                ProvideTypeBookList provideTypeBookList = new ProvideTypeBookList();
                ArrayList<ProvideTypeBook> provideTypeBookArrayList = new ArrayList<>();
                for (int i = 13; i < data.length; i++){
                    String subTypeBook = data[i].trim();
                    provideTypeBookArrayList.add(new ProvideTypeBook(bookType,subTypeBook));
                }
                Book bookInformation = new Book(bookName,bookShop,bookAuthor,bookISBN,bookType,bookDetail,bookPublisher,bookImg,bookStock,bookPage,leastStock,bookPrice,localDateTime,provideTypeBookArrayList);
                bookList.addBook(bookInformation);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Cannot read information in file " + filename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }

        return bookList;
    }

    public void writeData(BookList bookList){
        String path = filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            buffer.write(bookList.toCSV());

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                buffer.close();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
