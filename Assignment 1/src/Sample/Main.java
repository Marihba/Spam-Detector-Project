//Adam Bozzo Abhiram Sinnarajah Assignment 1 Main File
package Sample;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Main extends Application {
    // instances
    private TableView<TestFile> table;
    private BorderPane layout;
    private double accuracy;
    private double precision;

    //method that calculates accuracy and precision
    public void calculateAccPrec(WordCounter wordCounter){
        //list that contains keys from probability of file being spam tree map
        List<String> wordKeys2 = new ArrayList<>(wordCounter.probabilityFileSpam.keySet());
        //list that contains all the names for the spam files from test folder
        List<String> namesOfSpamTestListFile = Arrays.asList(wordCounter.namesOfSpamFileTest);
        boolean isSpam;//boolean to check if its spam or ham file
        //variables storing values for hypothesis checks
        int truePositive = 0;
        int trueNegative = 0;
        int falsePositive = 0;
        int falseNegative = 0;
        //loop that determines whether the file was a true negative/positive or false negative/positive
        for(String key: wordKeys2){
            isSpam = namesOfSpamTestListFile.contains(key);
            if(!isSpam && wordCounter.probabilityFileSpam.get(key) <= 0.5){
                trueNegative++;
            }else if (!isSpam && wordCounter.probabilityFileSpam.get(key) >= 0.5){
                falsePositive++;
            }else if(isSpam && wordCounter.probabilityFileSpam.get(key) >= 0.5){
                truePositive++;
            }else if(isSpam && wordCounter.probabilityFileSpam.get(key) <= 0.5){
                falseNegative++;
            }
        }
        //System.out.println("True Negative:"+trueNegative + "True Positive:" + truePositive + "False Positive:" + falsePositive + "False Negative" + falseNegative);
        accuracy = (double)(truePositive + trueNegative)/wordKeys2.size();
        precision = (double)(truePositive)/(falsePositive + truePositive);
    }
    
    public void barGraph(Stage primaryStage) throws Exception{
        
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        WordCounter wordCounter = new WordCounter();
        String path = "src\\Sample\\train\\ham";
        System.out.println("Processing:" +path);
        File dataDir = new File(path);
        String[] fileList = dataDir.list();
        try {
            wordCounter.processFile(dataDir,fileList);
            //wordCounter.outputWordCounts();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        path = "src\\Sample\\train\\spam";
        System.out.println("Processing:" +path);
        dataDir = new File(path);
        fileList = dataDir.list();
        try {
            wordCounter.processFile(dataDir, fileList);
            //wordCounter.outputWordCounts();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wordCounter.probabilityCalc();
        path = "src\\Sample\\test\\ham";
        System.out.println("Processing:" + path);
        dataDir = new File(path);
        wordCounter.namesOfHamFileTest = dataDir.list();
        try {
            wordCounter.processTestFile(dataDir);
            //wordCounter.outputWordCounts();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        path = "src\\Sample\\test\\spam";
        System.out.println("Processing:" + path);
        dataDir = new File(path);
        wordCounter.namesOfSpamFileTest = dataDir.list();
        try {
            wordCounter.processTestFile(dataDir);
            //wordCounter.outputWordCounts();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        calculateAccPrec(wordCounter);//calculates the accuracy and precision
        primaryStage.setTitle("Spam Master 3000");
        
        
        
        // creating File Menu
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenuItem);
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitMenuItem.setOnAction(e->System.exit(0));

        // creating statistics Menu
        Menu statsMenu = new Menu("Statistics");
        statsMenu.getItems().add(new MenuItem("Bar-Graph"));

        // setting up Menu Tabs at correct positions (first to last).
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(statsMenu);

        // Creating table
        table = new TableView<>();
        
        // Creating table columns
        TableColumn<TestFile, String> fileNameCol;
        fileNameCol = new TableColumn<>("File");
        fileNameCol.setMinWidth(300);
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        fileNameCol.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());
      
        TableColumn<TestFile, Float> actualNameCol;
        actualNameCol = new TableColumn<>("Actual Class");
        actualNameCol.setMinWidth(100);
        actualNameCol.setCellValueFactory(new PropertyValueFactory<>("FileType"));
      
        TableColumn<TestFile, Float> spamNameCol;
        spamNameCol = new TableColumn<>("Spam Probability");
        spamNameCol.setMinWidth(280);
        spamNameCol.setCellValueFactory(new PropertyValueFactory<>("SpamProbability"));
      
        // fill in the table
        table.getColumns().add(fileNameCol);
        table.getColumns().add(actualNameCol);
        table.getColumns().add(spamNameCol);
        table.setItems(DataSource.getAllFiles(wordCounter));   
        
        // Extra challenge: Including Add button
        GridPane addArea = new GridPane();  // what is the difference between grid and border pane?
        addArea.setPadding(new Insets(10, 10, 10, 10));
        addArea.setVgap(10);
        addArea.setHgap(10);

        // Creating Labels and TextFields for each piece of info
        Label accuracyLabel = new Label("Accuracy: ");
        //sidLabel.setStyle("-fx-Alignment: Center");
        addArea.add(accuracyLabel, 0, 0);

        TextField accuracyField = new TextField();
        accuracyField.setText(String.valueOf(accuracy));
        addArea.add(accuracyField, 1, 0);

        Label precisionLabel = new Label("Precision: ");
        addArea.add(precisionLabel, 0,2);

        TextField precisionField = new TextField();
        precisionField.setText(String.valueOf(precision));
        addArea.add(precisionField, 1 ,2);


        // setting up stage
        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(table);
        layout.setBottom(addArea);

        //Scene and stage setup
        Scene scene = new Scene(layout, 685, 600);
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}