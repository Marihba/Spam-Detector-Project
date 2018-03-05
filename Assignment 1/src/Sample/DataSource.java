//Adam Bozzo Abhiram Sinnarajah Assignment 1 Main File
package Sample;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSource {
    public static ObservableList<TestFile> getAllFiles(WordCounter wordCounter) {
        ObservableList<TestFile> statistics = FXCollections.observableArrayList();
        List<String> wordKeys2 = new ArrayList<>(wordCounter.probabilityFileSpam.keySet());
        List<String> namesOfSpamTestList = Arrays.asList(wordCounter.namesOfSpamFileTest);
        List<String> namesOfHamTestList = Arrays.asList(wordCounter.namesOfHamFileTest);  
        for(String key: wordKeys2){
            if(namesOfSpamTestList.contains(key)){
                statistics.add(new TestFile(key,"spam",wordCounter.probabilityFileSpam.get(key)));
            }else if(namesOfHamTestList.contains(key)){
                statistics.add(new TestFile(key,"ham",wordCounter.probabilityFileSpam.get(key)));
            }
        }
        return statistics;
    }
}