package Sample;
import java.text.DecimalFormat;
public class FileStats {

    private String fileName, fileType;
    private double spamProbability;


    public FileStats(String fileName, String fileType, double spamProbability) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.spamProbability = spamProbability;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public String getSpamProbability() {
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setSpamProbability(double spamProbability) {
        this.spamProbability = spamProbability;
    }
}