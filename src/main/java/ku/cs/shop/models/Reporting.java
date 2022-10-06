package ku.cs.shop.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Reporting {

    private String reportedAccount ;
    private String reportType ;
    private String imageName ;
    private String information ;
    private String reporter ;

    public Reporting(String reportedAccount, String reportType, String imageName, String information, String reporter) {
        this.reportedAccount = reportedAccount ;
        this.reportType = reportType ;
        this.imageName = imageName ;
        this.information = information ;
        this.reporter = reporter ;
    }

    public String getReportedAccount() {
        return reportedAccount;
    }
    public String getImageName() {
        return imageName;
    }
    public String getInformation() {
        return information;
    }
    public String getReporter() {
        return reporter;
    }
    public String getReportType() {
        return reportType;
    }

    public String getImagePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "images/report-images"
                + File.separator
                + imageName).toURI().toString();
    }

    public static void copyImageToPackage(File image, String imageName) {
        File file = new File("images/report-images") ;
        Path desPath = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
        try {
            Files.copy(image.toPath(), desPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toCsv() {
        return reportedAccount + "," + reportType + "," + imageName + ",\"" + information.replace("\"", "\"\"") + "\"," + reporter ;
    }

    @Override
    public String toString() {
        return reporter + ", รายงาน : " + reportType ;
    }
}
