package ku.cs.shop.services;

import com.opencsv.CSVReader;
import ku.cs.shop.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccountDataSource implements DataSource<AccountList> {
    private String filename ;

    public AccountDataSource(String filename) {
        this.filename = filename ;
    }

    @Override
    public AccountList readData() {

        AccountList accountList = new AccountList() ;

        try {
            FileReader fileReader = new FileReader(filename, StandardCharsets.UTF_8) ;
            CSVReader reader = new CSVReader(fileReader) ;
            String[] data = null ;

            while ((data = reader.readNext()) != null) {
                String type = data[0] ;

                if(type.equals("Admin")) {
                    String userName = data[1].trim() ;
                    String firstName = data[2].trim() ;
                    String lastName = data[3].trim() ;
                    String password = data[4].trim() ;
                    String birthDay = data[5].trim() ;
                    String birthMonth = data[6].trim() ;
                    String birthYear = data[7].trim() ;
                    String imageName = data[8].trim() ;
                    String phone = data[9].trim() ;
                    String sex = data[10].trim() ;
                    String address = data[11].trim() ;
                    String shopName = data[12].trim() ;
                    accountList.addAccount(new AdminAccount(
                            firstName, lastName, userName, password,
                            birthDay, birthMonth, birthYear,
                            imageName, phone, sex, address, shopName));
                }
                else if(type.equals("User")) {
                    String userName = data[1].trim() ;
                    String firstName = data[2].trim() ;
                    String lastName = data[3].trim() ;
                    String password = data[4].trim() ;
                    String birthDay = data[5].trim() ;
                    String birthMonth = data[6].trim() ;
                    String birthYear = data[7].trim() ;
                    String imageName = data[8].trim() ;
                    String phone = data[9].trim() ;
                    String sex = data[10].trim() ;
                    String address = data[11].trim() ;
                    String shopName = data[12].trim() ;
                    String status = data[13] ;
                    LocalDateTime loginTime = LocalDateTime.parse(data[14].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME) ;
                    int tryToLogin = Integer.parseInt(data[15].trim()) ;
                    accountList.addAccount(new UserAccount(
                            firstName, lastName, userName, password,
                            birthDay, birthMonth, birthYear,
                            imageName, phone, sex, address, shopName, status, loginTime, tryToLogin ));
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("Cannot read information in file " + filename);
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }

        return accountList;
    }

    @Override
    public void writeData(AccountList accountList) {
        String path = filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            buffer.write(accountList.toCsv());

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
