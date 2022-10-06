package ku.cs.shop.models;

public class AdminAccount extends Account {

    public AdminAccount(String firstName, String lastName, String userName, String password,
                   String birthDay, String birthMonth, String birthYear,
                   String imageName, String phone, String sex, String address, String shopName) {

        super(firstName, lastName, userName, password, birthDay, birthMonth, birthYear,
                imageName, phone, sex, address, shopName );
    }

    @Override
    public String toCsv() {
        return "Admin" + ","
                + getUserName() + ",\""
                + getFirstName().replace("\"", "\"\"") + "\",\""
                + getLastName().replace("\"", "\"\"") + "\","
                + getPassword() + ","
                + getBirthDay() + ","
                + getBirthMonth() + ","
                + getBirthYear() + ","
                + getImageName() + ","
                + getPhone() + ","
                + getSex() + ",\""
                + getAddress().replace("\"", "\"\"") + "\","
                + getShopName() ;
    }
}
