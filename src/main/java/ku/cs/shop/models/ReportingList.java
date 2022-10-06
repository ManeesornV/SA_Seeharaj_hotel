package ku.cs.shop.models;

import java.util.ArrayList;

public class ReportingList {

    private ArrayList<Reporting> reports ;
    private ArrayList<Reporting> currantReportedAccount ;

    public ReportingList() {
        reports = new ArrayList<>() ;
        currantReportedAccount = new ArrayList<>() ;
    }
    public ArrayList<Reporting> getCurrantReportedAccount() {
        return currantReportedAccount;
    }

    public void addReporting(Reporting reporting) {
        reports.add(reporting) ;
    }

    public void setCurrantReportedAccount(String accountName) {
        currantReportedAccount.clear();
        for (Reporting reporting: this.reports) {
            if (reporting.getReportedAccount().equals(accountName)) {
                currantReportedAccount.add(reporting) ;
            }
        }
    }

    public String toCsv() {
        String result = "" ;
        for (Reporting reporting: this.reports) {
            result += reporting.toCsv() + "\n";
        }
        return result ;
    }
}
