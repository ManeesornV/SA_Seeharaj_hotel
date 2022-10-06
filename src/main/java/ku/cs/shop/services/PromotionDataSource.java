package ku.cs.shop.services;

import com.opencsv.CSVReader;
import ku.cs.shop.models.Promotion;
import ku.cs.shop.models.PromotionList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PromotionDataSource implements DataSource<PromotionList> {

    private String filename ;

    public PromotionDataSource(String filename){
        this.filename = filename;
    }

    @Override
    public PromotionList readData() {
        PromotionList promotionList = new PromotionList();
        try{
            FileReader file = new FileReader(filename, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(file);
            String[] data = null;

            while ((data = reader.readNext()) != null) {
                String shopName = data[0].trim();
                String codePromotion = data[1].trim();
                int ratePrice = Integer.parseInt(data[2].trim());
                int priceReductionInPercentage = Integer.parseInt(data[3].trim());
                int priceReductionInBaht = Integer.parseInt(data[4].trim());
                String promotionDetail =  data[5].trim();
                LocalDateTime timeOfAddingPromotion = LocalDateTime.parse(data[6].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Promotion promotion = new Promotion(shopName, codePromotion, ratePrice, priceReductionInPercentage, priceReductionInBaht, promotionDetail, timeOfAddingPromotion);
                promotionList.addPromotion(promotion);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return promotionList;
    }

    @Override
    public void writeData(PromotionList promotionList) {
        String path = filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            buffer.write(promotionList.toCsv());

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
