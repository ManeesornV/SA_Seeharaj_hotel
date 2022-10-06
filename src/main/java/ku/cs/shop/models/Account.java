package ku.cs.shop.models;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

public abstract class Account {

    private String filename ;

    private String firstName ;
    private String lastName ;
    private String userName ;
    private String password ;
    private String birthDay ;
    private String birthMonth ;
    private String birthYear ;
    private String imageName ;
    private String phone ;
    private String sex ;
    private String shopName ;
    private String address ;
    private static boolean passwordCheck = false ;
    private static boolean passwordCondition = false ;
    private static boolean userNameCheck = false ;

    //เก็บค่าเริ่มต้น
    public Account() {}
    public Account(String firstName, String lastName, String userName, String password,
                String birthDay, String birthMonth, String birthYear,
                String imageName, String phone, String sex, String address, String shopName) {

        this.firstName = firstName ;
        this.lastName = lastName ;
        this.userName = userName ;
        this.password = password ;
        this.birthDay = birthDay ;
        this.birthMonth = birthMonth ;
        this.birthYear = birthYear ;
        this.imageName = imageName ;
        this.phone = phone ;
        this.sex = sex ;
        this.address = address ;
        this.shopName = shopName ;

    }


    public Account(String filename){
        this.filename = filename;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getBirthDay() { return birthDay; }
    public String getBirthMonth() {
        return birthMonth;
    }
    public String getBirthYear() {
        return birthYear;
    }
    public String getImageName() { return imageName; }
    public String getPhone() { return phone; }
    public String getSex() { return sex; }
    public String getAddress() {
        return address;
    }
    public String getShopName() { return shopName; }

    public static boolean getPasswordCondition() { return  passwordCondition; }
    public static boolean getPasswordCheck() { return passwordCheck; }
    public static boolean getUserNameCheck() {return userNameCheck; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setImageName(String imageName){this.imageName = imageName;}
    public void setPhone(String phone){this.phone = phone;}
    public void setSex(String sex){this.sex = sex; }

    public String getImagePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "images/account-images"
                + File.separator
                + imageName).toURI().toString();
    }

    //เก็บรูปภาพ
    public static void copyImageToPackage(File image, String imageName) {
        File file = new File("images/account-images") ;
        Path desPath = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
        try {
            Files.copy(image.toPath(), desPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ตรวจสอบ username ว่าตรงเงื่อนไขมั้ย
    public static String checkUserNameCondition(String userName) {
        if (!Pattern.matches("[a-zA-Z0-9]+", userName)) {
            userNameCheck = false ;
            return "ชื่อผู้ใช้ไม่ตรงตามรูปแบบที่กำหนด" ;
        }
        userNameCheck = true ;
        return null ;
    }

    //ตรวจสอบรหัสผ่านตามเงื่อนไข
    public static boolean checkPasswordCondition (String password)  {
        if (password.length()<8 || !(Pattern.matches("[a-zA-Z0-9]+", password)&&!Pattern.matches("[a-zA-Z]+", password)&&!Pattern.matches("[0-9]+", password))) {
            passwordCondition = false ;
            return false ;
        }
        else {
            passwordCondition = true ;
            return true ; }
    }

    //ตรวจสอบเบอร์โทรตามเงื่อนไข
    public boolean checkPhoneNumber (String phone){
        if(phone.length() == 10 && (Pattern.matches("[0-9]+", phone))){
            return true;
        }
        else{
            return false;
        }
    }

    public String checkPhoneNumberIsCorrect(String phone) {
        if (checkPhoneNumber(phone)) {
            return "";
        } else {
            return "เบอร์โทรไม่ถูกต้อง กรุณาตรวจสอบ";
        }
    }

    //ตรวจสอบการยืนยันรหัสผ่าน
    public static String comparePassword(String password, String passwordRecheck) {
        if (password.equals(passwordRecheck)) {
            passwordCheck = true ;
            return "";
        }
        else {
            passwordCheck = false ;
            return "รหัสผ่านไม่ตรงกัน โปรดตรวจสอบรหัสผ่าน";
        }
    }

    public abstract String toCsv();

    @Override
    public String toString() {
        return userName + " : " + firstName + "   " + lastName ;
    }
}
