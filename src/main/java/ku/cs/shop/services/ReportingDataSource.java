package ku.cs.shop.services;

import com.opencsv.CSVReader;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.Reporting;
import ku.cs.shop.models.ReportingList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReportingDataSource implements DataSource<ReportingList> {

    private String filename ;

    public ReportingDataSource(String filename) {
        this.filename = filename ;
    }

    @Override
    public ReportingList readData() {

        ReportingList reportingList = new ReportingList();

        try {
            FileReader fileReader = new FileReader(filename, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(fileReader);
            String[] data = null;

            while ((data = reader.readNext()) != null) {
                String reportedAccount = data[0].trim();
                String reportType = data[1].trim();
                String imageName = data[2].trim();
                String information = data[3].trim();
                String reporter = data[4].trim();

                reportingList.addReporting(new Reporting(reportedAccount, reportType, imageName, information, reporter));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reportingList ;
    }

    @Override
    public void writeData(ReportingList reportingList) {
        String path = filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            buffer.write(reportingList.toCsv());

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
