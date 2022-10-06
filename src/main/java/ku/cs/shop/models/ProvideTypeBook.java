package ku.cs.shop.models;

public class ProvideTypeBook {
    private String superTypeBook;
    private String subTypeBook;

    public ProvideTypeBook(){};


    public ProvideTypeBook(String superTypeBook, String subTypeBook) {
        this.superTypeBook = superTypeBook;
        this.subTypeBook = subTypeBook;
    }


    public String getSuperTypeBook() { return superTypeBook; }
    public String getSubTypeBook() { return subTypeBook; }

    public void setSuperTypeBook(String superTypeBook) { this.superTypeBook = superTypeBook; }
    public void setSubTypeBook(String subTypeBook) { this.subTypeBook = subTypeBook; }


    public String toCsv(){
        String result = "";
        result = superTypeBook + ","
                + subTypeBook +  "\n" ;
        return result;
    }

}
