//Adam Bozzo Abhiram Sinnarajah Assignment 1 Main File
package Sample;
import java.text.DecimalFormat;
public class TestFile {

    private String fileName;
    private String actualClass;
    private double spamProbability;

    public TestFile(String fileName, String fileType, double spamProbability) {
        this.fileName = fileName;
        this.actualClass = fileType;
        this.spamProbability = spamProbability;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileType() {
        return this.actualClass;
    }

    public String getSpamProbability() {
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.actualClass = fileType;
    }

    public void setSpamProbability(double spamProbability) {
        this.spamProbability = spamProbability;
    }
}