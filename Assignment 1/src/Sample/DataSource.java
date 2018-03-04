package Sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSource {
    public static ObservableList<FileStats> getAllStudents(WordCounter wordCounter) {
        ObservableList<FileStats> statistics = FXCollections.observableArrayList();
        for (int i = 0; i < wordCounter.namesOfHamFileTrain.length; i++){
            statistics.add(new FileStats(wordCounter.namesOfHamFileTrain[i],"ham",0));
        }
        for (int i = 0; i < wordCounter.namesOfSpamFileTrain.length; i++){
            statistics.add(new FileStats(wordCounter.namesOfSpamFileTrain[i],"spam",0));
        }
        return statistics;
    }
}