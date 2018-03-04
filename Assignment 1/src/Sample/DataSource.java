package Sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSource {
    public static ObservableList<FileStats> getAllStudents(WordCounter wordCounter) {
        ObservableList<FileStats> statistics = FXCollections.observableArrayList();
        for (int i = 0; i < wordCounter.namesOfHamFile.length; i++){
            statistics.add(new FileStats(wordCounter.namesOfHamFile[i],"ham",0));
        }
        for (int i = 0; i < wordCounter.namesOfSpamFile.length; i++){
            statistics.add(new FileStats(wordCounter.namesOfSpamFile[i],"spam",0));
        }
        return statistics;
    }
}