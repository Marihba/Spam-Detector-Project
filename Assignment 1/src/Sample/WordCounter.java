//Adam Bozzo Abhiram Sinnarajah Assignment 1 Processing Word File
package Sample;
import java.io.*;
import java.util.*;
import java.lang.Math;
import java.util.stream.Stream;

public class WordCounter {
    //different tree maps being used
    private Map<String,Integer> wordCounts;
    private List<String> wordKeys;
    private Map<String,Integer> trainHamFreq;
    private Map<String,Integer> trainSpamFreq;
    Map<String,Double> probabilityWordSpam;
    Map<String,Double> probabilityWordHam;
    Map<String,Double> probabilityWordTotal;
    Map<String,Double> probabilityFileSpam;
    //arrays containing names of the files
    String[] namesOfHamFileTrain;
    String[] namesOfSpamFileTrain;
    String[] namesOfTestFile;
    String[] namesOfHamFileTest;
    String[] namesOfSpamFileTest;
    
    private List<String> currentFile = new ArrayList<>();//list that contains words of current file being worked on
    private String hamOrSpam;//string that determines if it is ham or spam file
    
    // Constructor to initialize object and tree map
    public WordCounter() {
        wordCounts = new TreeMap<>();
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        probabilityWordHam = new TreeMap<>();
        probabilityWordSpam = new TreeMap<>();
        probabilityWordTotal = new TreeMap<>();
        probabilityFileSpam = new TreeMap<>();
    }
    
    //method to calculate probability that the word is spam
    public void probabilityCalc(){
        double spamProb;
        //determines probability of word being spam from train ham set
        wordKeys = new ArrayList<>(trainHamFreq.keySet());
        for (String key: wordKeys){
            spamProb = (double)trainHamFreq.get(key)/namesOfHamFileTrain.length;
            probabilityWordHam.put(key, spamProb);
        }
        //determines probability of word being spam from spam train set
        wordKeys = new ArrayList<>(trainSpamFreq.keySet());
        for (String key: wordKeys){
            spamProb = (double)trainSpamFreq.get(key)/namesOfSpamFileTrain.length;
            probabilityWordSpam.put(key, spamProb);
        }
        wordKeys.addAll(trainHamFreq.keySet());//combines the two spam probability from ham and spam
        
        //loop to generate tree map from training for probability of spam for each word
        for(String key: wordKeys){
            if(probabilityWordHam.containsKey(key) && probabilityWordSpam.containsKey(key)){
                spamProb = probabilityWordSpam.get(key)/(probabilityWordSpam.get(key)+probabilityWordHam.get(key));  
                probabilityWordTotal.put(key,spamProb);
            }else if(!probabilityWordHam.containsKey(key) && probabilityWordSpam.containsKey(key)){
                probabilityWordTotal.put(key, 1.0);
            }else if(probabilityWordHam.containsKey(key) && !probabilityWordSpam.containsKey(key)){
                probabilityWordTotal.put(key, 0.0);
            }
        }
        wordKeys = new ArrayList<>(probabilityWordTotal.keySet());
    }
    //method to process the test files
    public void processTestFile(File file) throws IOException{
        double probVarSum = 0.0;
        double spamChance;
        List<String> tempWord = new ArrayList<>();
        if (file.isDirectory()) {   // if given file path is a directory
            // process all the files in that directory
            File[] contents = file.listFiles();
            for (File current: contents) {
                processTestFile(current);
            }
        }
        else if (file.exists()) {
            // count the words in this file
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\s");
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    if(!tempWord.contains(word)){
                        tempWord.add(word);
                    }
                }
            }
            //calculates the summation variable that will be used in spam chance equation
            for(String key: tempWord){
                if(probabilityWordTotal.containsKey(key)){
                    if(probabilityWordTotal.get(key) > 0.0 && probabilityWordTotal.get(key) != -0.0 
                    && probabilityWordTotal.get(key) < 1.0){
                        probVarSum += Math.log(1.0-probabilityWordTotal.get(key)) - (Math.log(probabilityWordTotal.get(key)));
                    }
                }
            }
            tempWord.clear();//clears out the list that contains all words in file so it is clean for next file
            spamChance = 1.0/(1.0+Math.exp(probVarSum));//calculates the spam chance for this file
            probabilityFileSpam.put(file.getName(), spamChance);//stores spam chance into tree map
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
        }else if (hamOrSpam.contains("spam")){
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

