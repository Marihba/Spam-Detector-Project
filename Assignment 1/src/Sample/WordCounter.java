package Sample;
import java.io.*;
import java.util.*;
import java.lang.Math;

public class WordCounter {
    // Private data fields
    private Map<String,Integer> wordCounts;
    private List<String> wordKeys;
    private Map<String,Integer> trainHamFreq;
    private Map<String,Integer> trainSpamFreq;
    Map<String,Double> probabilityWordSpam;
    Map<String,Double> probabilityWordHam;
    Map<String,Double> probabilityWordTotal;
    String[] namesOfHamFileTrain;
    String[] namesOfSpamFileTrain;
    private List<String> currentFile = new ArrayList<>();
    private String hamOrSpam;
    // Constructor
    public WordCounter() {
        wordCounts = new TreeMap<>();
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        probabilityWordHam = new TreeMap<>();
        probabilityWordSpam = new TreeMap<>();
        probabilityWordTotal = new TreeMap<>();
    }
    
    public void probabilityCalc(){
        double spamProb;
        wordKeys = new ArrayList<>(trainHamFreq.keySet());
        for (String key: wordKeys){
            spamProb = trainHamFreq.get(key)/namesOfHamFileTrain.length;
            probabilityWordHam.put(key, spamProb);
        }
        wordKeys = new ArrayList<>(trainSpamFreq.keySet());
        for (String key: wordKeys){
            spamProb = trainSpamFreq.get(key)/namesOfSpamFileTrain.length;
            probabilityWordSpam.put(key, spamProb);
        }
        wordKeys.addAll(trainHamFreq.keySet());
        for(String key: wordKeys){
            if(probabilityWordHam.containsKey(key) && probabilityWordSpam.containsKey(key)){
                spamProb = trainSpamFreq.get(key)/(trainSpamFreq.get(key)+trainHamFreq.get(key));  
                probabilityWordTotal.put(key,spamProb);
            }else if(!probabilityWordHam.containsKey(key) && probabilityWordSpam.containsKey(key)){
                probabilityWordTotal.put(key, 1.0);
            }else if(probabilityWordHam.containsKey(key) && !probabilityWordSpam.containsKey(key)){
                probabilityWordTotal.put(key, 0.0);
            }
        }
        wordKeys = new ArrayList<>(probabilityWordTotal.keySet());
        /*for(String key: wordKeys){
            System.out.println(key + ":" + probabilityWordTotal.get(key));
        }*/
        double probVarSum = 0.0;
        for(String key: wordKeys){
            probVarSum = probVarSum + Math.log(1-probabilityWordTotal.get(key))-Math.log(probabilityWordTotal.get(key));
            
        }
    }
    
    public void processFile(File file, String[] fileList) throws IOException {
        hamOrSpam = file.getAbsolutePath();
            if(hamOrSpam.contains("ham")){
                namesOfHamFileTrain = fileList;
            }else{
                namesOfSpamFileTrain = fileList;
            }
        if (file.isDirectory()) {   // if given file path is a directory

            // process all the files in that directory
            File[] contents = file.listFiles();
            for (File current: contents) {
                processFile(current, fileList);
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
            currentFile.clear();
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
        if(fileName.contains("ham")){
             if (trainHamFreq.containsKey(word) && !currentFile.contains(word)) {
                int oldCount = trainHamFreq.get(word);
                trainHamFreq.put(word,oldCount+1);
            }
            else if (!trainHamFreq.containsKey(word)){
                trainHamFreq.put(word,1);
            }
            if(!currentFile.contains(word)){
                currentFile.add(word);
            }
        }else if(fileName.contains("spam")){
            if (trainSpamFreq.containsKey(word) && !currentFile.contains(word)) {
                int oldCount = trainSpamFreq.get(word);
                trainSpamFreq.put(word,oldCount+1);
            }
            else if (!trainSpamFreq.containsKey(word)){
                trainSpamFreq.put(word,1);
            }
            if(!currentFile.contains(word)){
                currentFile.add(word);
            }
        }
    }
  
    public void outputWordCounts(){
        if(hamOrSpam.contains("ham")){
            System.out.println("# of words: " + trainHamFreq.keySet().size());
            wordKeys = new ArrayList<>(trainHamFreq.keySet());
            for (String key: wordKeys){
                System.out.println(key + ": " + trainHamFreq.get(key));
            }
            System.out.println("Total number of files:" + namesOfHamFileTrain.length);
        }else if (hamOrSpam.contains("testFile")){
            System.out.println("# of words: " + trainSpamFreq.keySet().size());
            wordKeys = new ArrayList<>(trainSpamFreq.keySet());
            for (String key: wordKeys){
                System.out.println(key + ": " + trainSpamFreq.get(key));
            }
            System.out.println("Total number of files:" + namesOfSpamFileTrain.length);
        }
        //print out file names
        //for(int k = 0; k < namesOfFile.size(); k++){
        //    System.out.println(namesOfFile.get(k));
        //}
    }
}

