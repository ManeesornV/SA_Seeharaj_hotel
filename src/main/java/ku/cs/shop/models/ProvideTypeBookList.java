package ku.cs.shop.models;

import java.util.*;

public class ProvideTypeBookList {
    private ArrayList<ProvideTypeBook> typeBooks;
    private Set<String> superTypeBook;

    public ProvideTypeBookList(){
        typeBooks = new ArrayList<>();
        superTypeBook = new HashSet<>();
    }

    public void addTypeBook(ProvideTypeBook provideTypeBook){
        typeBooks.add(provideTypeBook);
        superTypeBook.add(provideTypeBook.getSuperTypeBook());
    }

    // ค้นหา subTypeBook จากข้อมูลประเภททั้งหมด
    public ArrayList<ProvideTypeBook> findSubTypeBook(String typeBook){
        ArrayList<ProvideTypeBook> provideTypeBookArrayList = new ArrayList<>();
        for(ProvideTypeBook provideTypeBook : typeBooks){
            if (provideTypeBook.getSuperTypeBook().equals(typeBook)){
                provideTypeBookArrayList.add(provideTypeBook);
            }
        }
        return provideTypeBookArrayList;
    }

    // คืนค่าจำนวนค้นหา subTypeBook จากข้อมูลประเภททั้งหมด
    public int numOfSubTypeBook(String typeBook){
        ArrayList<String> provideSubTypeBookArrayList = new ArrayList<>();
        for(ProvideTypeBook provideTypeBook : typeBooks){
            if (provideTypeBook.getSuperTypeBook().equals(typeBook)){
                provideSubTypeBookArrayList.add(provideTypeBook.getSubTypeBook());
            }
        }
        return provideSubTypeBookArrayList.size();
    }

    public Set<String> getSuperTypeBook() { return superTypeBook; }

    // ตรวจสอบการมีอยู่ของชื่อ typeBook
    public boolean checkNewTypeBookHaveUsed(String typeBook){
        for(ProvideTypeBook provideTypeBook : this.typeBooks) {
            if (provideTypeBook.getSuperTypeBook().equals(typeBook)) {
                return true;
            }
        }
        return false;
    }

    public String toCSV() {
        String csv = "" ;
        for(ProvideTypeBook provideTypeBook: typeBooks) {
            csv += provideTypeBook.toCsv();
        }
        return csv;
    }
}
