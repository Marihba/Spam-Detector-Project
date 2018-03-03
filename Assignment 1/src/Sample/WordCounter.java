package Sample;
import java.io.*;
import java.util.*;

public class WordCounter {
    // Private data fields
    private Map<String,Integer> wordCounts;
    private List<String> wordKeys;
    private Map<String,Integer> trainHamFreq;
    private List<String> namesOfFile;

    // Constructor
    public WordCounter() {
        wordCounts = new TreeMap<>();
        trainHamFreq = new TreeMap<>();
        namesOfFile = new ArrayList<>();
    }

    public void processFile(File file) throws IOException {
        if (file.isDirectory()) {   // if given file path is a directory

            // process all the files in that directory
            File[] contents = file.listFiles();
            for (File current: contents) {
                processFile(current);
            }
        }
        else if (file.exists()) {
            // count the words in this file
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\s");
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    countWord(word, file);
                }
            }
        }
    }

    private boolean isWord(String word) {
        String pattern = "^[a-zA-Z]+$";
        if (word.matches(pattern)) {
            return true;
        }
        else {
            return false;
        }
    }


    private void countWord(String word, File file) {
        String fileName = file.getAbsolutePath();
        if(namesOfFile.isEmpty()){
            namesOfFile.add(fileName);
            System.out.println("Just added " + fileName);
        }
        if(!namesOfFile.contains(fileName)) { // this was the issue, you had to make sure
                                                // that not only if the file does not exit for a different word, but it must
                                                // also consider the fact that the arraylist containing the files must not
                                                // overlap.
            namesOfFile.add(fileName);
            System.out.println("Just added " + fileName);
        }
        if (wordCounts.containsKey(word) && !namesOfFile.contains(fileName)) {
            int oldCount = trainHamFreq.get(word);
            trainHamFreq.put(word,oldCount+1);
        }
        else {
            trainHamFreq.put(word,1);
        }
        if (wordCounts.containsKey(word)) {
            int oldCount = wordCounts.get(word);
            wordCounts.put(word, oldCount+1);
        }
        else {
            wordCounts.put(word, 1);
        }
    }
  
    public void outputWordCounts(){
        System.out.println("# of words: " + wordCounts.keySet().size());
        wordKeys = new ArrayList<>(trainHamFreq.keySet());
        for (String key: wordKeys){
            System.out.println(key + ": " + trainHamFreq.get(key));
        }
        System.out.println("Total number of files:" + namesOfFile.size());
        //print out file names
        //for(int k = 0; k < namesOfFile.size(); k++){
        //    System.out.println(namesOfFile.get(k));
        //}
    }
}

