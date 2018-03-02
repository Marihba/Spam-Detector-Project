package sample;
import java.io.*;
import java.util.*;
public class WordCounter {
  private Map<String,Integer> wordCounts;
  private List<String> wordKeys;
  private Map<String,Integer> trainHamFreq;
  private List<String> namesOfFile;
  public WordCounter() {
    wordCounts = new TreeMap<>();
    trainHamFreq = new TreeMap<>();
    namesOfFile = new ArrayList<>();
  }
  public void processFile(File file) throws IOException {
    if (file.isDirectory()) {
      // process all the files in that directory
      File[] contents = file.listFiles();
      for (File current: contents) {
        processFile(current);
      }
    } else if (file.exists()) {
      // count the words in this file
      Scanner scanner = new Scanner(file);
      scanner.useDelimiter("\\s");//"[\s\.;:\?\!,]");//" \t\n.;,!?-/\\");
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
    } else {
      return false;
    }
    // also fine:
    //return word.matches(pattern);
  }
  private void countWord(String word, File file) {
    String fileName = file.getAbsolutePath();
    boolean fileExist = true;  
    if(namesOfFile.isEmpty()){
        namesOfFile.add(fileName);
    }else{
        for(int k = 0; k < namesOfFile.size() && fileExist == true; k++){
            if(!fileName.equalsIgnoreCase(namesOfFile.get(k))){
                fileExist = false; 
            }
        }
    }
    if(fileExist == false){
        namesOfFile.add(fileName);
    }
    if (wordCounts.containsKey(word) && fileExist == false) {
      int oldCount = trainHamFreq.get(word);
      trainHamFreq.put(word,oldCount+1);     
    } else {
      trainHamFreq.put(word,1);
    }
    if (wordCounts.containsKey(word)) {
      int oldCount = wordCounts.get(word);
      wordCounts.put(word, oldCount+1);     
    } else {
      wordCounts.put(word, 1);
    }
  }
  
  public void outputWordCounts(int minCount){
    System.out.println("# of words: " + wordCounts.keySet().size());
    wordKeys = new ArrayList<String>(trainHamFreq.keySet());
    for (String key: wordKeys){
        System.out.println(key + ": " + trainHamFreq.get(key));
    }
    System.out.println("Array of files size:" + namesOfFile.size());
    //print out file names
    for(int k = 0; k < namesOfFile.size(); k++){
        System.out.println(namesOfFile.get(k));
    }
  }
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: java WordCounter <dir> <outfile>");
      System.exit(0);
    }
    WordCounter wordCounter = new WordCounter();
    String path = args[0];
    File dataDir = new File(path);
    try {
      wordCounter.processFile(dataDir);
      wordCounter.outputWordCounts(2);
    } catch (FileNotFoundException e) {
      System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
      e.printStackTrace();
    } catch (IOException e) {
        System.out.println("hello");
      e.printStackTrace();
    }
  }
}

