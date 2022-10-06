package ku.cs.shop.models;

import ku.cs.shop.services.AccountSortByTimeComparator;
import ku.cs.shop.services.BookSortByTimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountList {

    private ArrayList<Account> accounts ;
    private ArrayList<UserAccount> userAccounts ;
    private Account currentAccount ;

    public AccountList() {
        accounts = new ArrayList<>();
        userAccounts = new ArrayList<>();
    }

    public Account getCurrentAccount() {
        return currentAccount ;
    } //เก็บ username ที่ login เข้ามา

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<UserAccount> getUserAccounts() {
        spitUserAccountAndAdminAccount() ;
        AccountSortByTimeComparator comparator = new AccountSortByTimeComparator() ;
        this.userAccounts.sort(comparator);
        return userAccounts ;
    }

    public void addAccount(Account account) {
        accounts.add(account) ;
    }

    //ตรวจสอบ userName ว่าซ้ำมั้ย
    public boolean checkUserNameHaveUsed(String userName) {
        for (Account account: this.accounts) {
            if (account.getUserName().equals(userName) ) {
                return true;
            }
        }
        return false;
    }

    //ตรวจสอบ shopName ว่าซ้ำมั้ย
    public boolean checkShopNameHaveUsed(String shopName) {
        for (Account account: this.accounts) {
            if (account.getShopName().equals(shopName) ) {
                return true;
            }
        }
        return false;
    }

    public Account login(String userName, String password) {
        for (Account account: this.accounts) {
            if (account.getUserName().equals(userName)) {
                if (account.getPassword().equals(password)) {
                    currentAccount = account ;
                    return account ;
                }
            }
        }
        return null ;
    }

    //แยกลิส แอดมิน กับ ผู้ใช้
    public void spitUserAccountAndAdminAccount() {
        userAccounts.clear();
        for (Account account : this.accounts) {
            if ( account instanceof UserAccount) {
                userAccounts.add((UserAccount) account) ;
            }
        }
    }

    //เรียง Account ตามเวลา Login
    public void sort(Comparator<UserAccount> accountComparator) {Collections.sort(this.userAccounts, accountComparator) ; }


    public String toCsv() {
        String result = "" ;
        for (Account account: this.accounts) {
            result += account.toCsv() + "\n";
        }
        return result ;
    }
}
