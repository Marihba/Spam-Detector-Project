//Adam Bozzo Abhiram Sinnarajah Assignment 1 Main File
package Sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        MenuItem barGraphMenuItem = new MenuItem("Bar-Graph");
        statsMenu.getItems().add(barGraphMenuItem);
        barGraphMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));


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
        
        // Including Add are for accuraccy and precision
        GridPane addArea = new GridPane();  // what is the difference between grid and border pane?
        addArea.setPadding(new Insets(10, 10, 10, 10));
        addArea.setVgap(10);
        addArea.setHgap(10);

        // Creating Labels and TextField for accuracy
        Label accuracyLabel = new Label("Accuracy: ");
        //sidLabel.setStyle("-fx-Alignment: Center");
        addArea.add(accuracyLabel, 0, 0);

        TextField accuracyField = new TextField();
        accuracyField.setText(String.valueOf(accuracy));
        addArea.add(accuracyField, 1, 0);

        // Creating Label and TextFields for precision
        Label precisionLabel = new Label("Precision: ");
        addArea.add(precisionLabel, 0,2);

        TextField precisionField = new TextField();
        precisionField.setText(String.valueOf(precision));
        addArea.add(precisionField, 1 ,2);

        // graphs
        Group root = new Group();
        Canvas canvas = new Canvas(400,400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // creating legend
        gc.setFill(Color.BLACK);
        gc.fillText("Legend",225,113);

        // legend universe
        gc.setStroke(Color.BLACK);
        gc.strokeRect(220,118,98,70);

        // true positive in legend
        gc.fillText("True Positive", 225, 135);
        gc.setFill(Color.YELLOW);
        gc.fillRect(305,125,10,10);

        // false positive in legend
        gc.setFill(Color.BLACK);
        gc.fillText("False Positive", 225, 150);
        gc.setFill(Color.RED);
        gc.fillRect(305,140,10,10);

        // true negative in legend
        gc.setFill(Color.BLACK);
        gc.fillText("True Negative", 225, 165);
        gc.setFill(Color.GREEN);
        gc.fillRect(305, 155, 10, 10);

        // false negative in legend
        gc.setFill(Color.BLACK);
        gc.fillText("False Negative", 225, 180);
        gc.setFill(Color.BLUE);
        gc.fillRect(305, 170, 10, 10);

        // Bar graph Universe
        gc.setStroke(Color.BLACK);
        gc.strokeRect(25, 100, 300, 250);


        // setting up axis for graph
        gc.setStroke(Color.BLACK);
        // x-axis
        gc.strokeLine(85, 300, 220, 300);
        // y-axis
        gc.strokeLine(85, 155, 85,  300);
        // ticks on the y axis (# of files...)
        gc.setFill(Color.BLACK);
        gc.fillText("200", 59, 287);
        gc.fillText("400", 59, 267);
        gc.fillText("600", 59, 247);
        gc.fillText("800", 59, 227);
        gc.fillText("1000", 53, 207);
        gc.fillText("1200", 53, 187);
        gc.fillText("1400", 53, 167);
        gc.fillRect(80, 290, 10, 2);
        gc.fillRect(80, 280, 10, 2);
        gc.fillRect(80, 270, 10, 2);
        gc.fillRect(80, 260, 10, 2);
        gc.fillRect(80, 250, 10, 2);
        gc.fillRect(80, 240, 10, 2);
        gc.fillRect(80, 230, 10, 2);
        gc.fillRect(80, 220, 10, 2);
        gc.fillRect(80, 210, 10, 2);
        gc.fillRect(80, 200, 10, 2);
        gc.fillRect(80, 190, 10, 2);
        gc.fillRect(80, 180, 10, 2);
        gc.fillRect(80, 170, 10, 2);
        gc.fillRect(80, 160, 10, 2);



        gc.setFill(Color.BLACK);
        gc.fillText("Positives and Negatives", 91, 315);
        gc.fillText("# of\nFiles", 30,225);

        // filling graph with data

        // true positive
        gc.setFill(Color.YELLOW);
        gc.fillRect(95, 166, 20, 133);

        // false positive
        gc.setFill(Color.RED);
        gc.fillRect(125, 276, 20, 23);

        // true negative
        gc.setFill(Color.GREEN);
        gc.fillRect(155, 187, 20, 112);

        // false negative
        gc.setFill(Color.BLUE);
        gc.fillRect(185, 295, 20, 4);

        gc.setFill(Color.BLACK);
        gc.fillText("Correct vs Incorrect\n        Predictions", 95, 140);

        // setting up stage
        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(table);
        layout.setBottom(addArea);
        //Scene and stage setup
        //Scene scene = new Scene(layout, 685, 600);
        Scene scene = new Scene(layout, 650, 600);
        
        //print out primary stage
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //event handler to create popup screen when statistics menu item is pressed
        barGraphMenuItem.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout = new BorderPane();              
                final Stage statisticsPop = new Stage();
                layout.setTop(canvas);
                Scene statisticsScene = new Scene(layout, 350, 420);
                statisticsPop.setTitle("Predictions");
                statisticsPop.setScene(statisticsScene);
                statisticsPop.show();
            }
         });
    
    }

    public static void main(String[] args) {
        launch(args);
    }
}