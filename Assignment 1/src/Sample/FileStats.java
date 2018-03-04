package Sample;
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

    public double getSpamProbability() {
        return this.spamProbability;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAssignment(String fileType) {
        this.fileType = fileType;
    }

    public void setSpamProbability(double spamProbability) {
        this.spamProbability = spamProbability;
    }
}