package ku.cs.shop.services;

import com.opencsv.CSVReader;
import ku.cs.shop.models.Reviews;
import ku.cs.shop.models.ReviewsList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewsDataSource implements DataSource<ReviewsList> {

    private String filename ;

    public ReviewsDataSource(String filename){
        this.filename = filename;
    }

    @Override
    public ReviewsList readData() {
        ReviewsList reviewsList = new ReviewsList();

        try{
            FileReader file = new FileReader(filename, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(file);
            String[] data = null;

            while ((data = reader.readNext()) != null) {
                String bookName = data[0].trim();
                String bookShop = data[1].trim();
                String userName = data[2].trim();
                String imageName = data[3].trim();
                String comment = data[4].trim();
                int bookRating =  Integer.parseInt(data[5].trim());
                LocalDateTime localDateTime = LocalDateTime.parse(data[6].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Reviews reviews = new Reviews(bookName, bookShop, userName, imageName, comment, bookRating, localDateTime);
                reviewsList.addReviews(reviews);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return reviewsList;
    }

    @Override
    public void writeData(ReviewsList reviewsList) {
        String path = filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            buffer.write(reviewsList.toCsv());

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