package ku.cs.shop.services;

import com.opencsv.CSVReader;
import ku.cs.shop.models.Order;
import ku.cs.shop.models.OrderList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class OrderDataSource implements DataSource<OrderList>{

    private String filename;
    public OrderDataSource(){}
    public OrderDataSource(String filename) { this.filename = filename; }

    public OrderList readData() {

        OrderList orderList = new OrderList();

        try {
            FileReader file = new FileReader(filename, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(file);
            String[] data = null;

            while ((data = reader.readNext()) != null) {
                String bookImage = data[0].trim();
                String bookName = data[1].trim();
                String bookShop = data[2].trim();
                int totalBookOrdered = Integer.parseInt(data[3].trim());
                double totalPriceOrdered = Double.parseDouble(data[4].trim());
                String trackingNumber = data[5].trim();
                String customerName = data[6].trim();
                String customerPhone = data[7].trim();
                LocalDateTime timeOfOrdered = LocalDateTime.parse(data[8].trim());

                Order orderData = new Order(bookImage,bookName,bookShop,totalBookOrdered,totalPriceOrdered,trackingNumber,customerName,customerPhone,timeOfOrdered);
                orderList.addOrder(orderData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read information in file " + filename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }

        return orderList;
    }

    public void writeData(OrderList orderList) {
        String path = filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            buffer.write(orderList.toCSV());

        } catch (IOException e){
            e.printStackTrace();

        } finally {
            try {
                buffer.close();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
