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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    // instances
    private TableView<FileStats> table;
    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        WordCounter wordCounter = new WordCounter();
        String path = "src\\Sample\\train\\ham";
        System.out.println(path);
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
        System.out.println(path);
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
        primaryStage.setTitle("Spam Master 3000");
        // creating File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new Menu("New SpamFilter");
        newMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        fileMenu.getItems().add(newMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Open..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new Menu("Save"));
        fileMenu.getItems().add(new Menu("Save As..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenuItem);
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitMenuItem.setOnAction(e->System.exit(0));

        // creating statistics Menu
        Menu statsMenu = new Menu("Statistics");
        //MenuItem newMenuItem = new Menu("Pie-Graph");
        statsMenu.getItems().add(new MenuItem("Pie-Graph"));
        //statsMenu.getItems().add(newMenuItem);
        statsMenu.getItems().add(new SeparatorMenuItem());
        statsMenu.getItems().add(new MenuItem("Bar-Graph"));

        // setting up Menu Tabs at correct positions (first to last).
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(statsMenu);

        // Creating table
        table = new TableView<>();
             // check with false after

        // Creating table columns
        TableColumn<FileStats, String> fileNameCol;
        fileNameCol = new TableColumn<>("File");
        fileNameCol.setMinWidth(300);
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        fileNameCol.setCellFactory(TextFieldTableCell.<FileStats>forTableColumn());
      
        TableColumn<FileStats, Float> actualNameCol;
        actualNameCol = new TableColumn<>("Actual Class");
        actualNameCol.setMinWidth(100);
        actualNameCol.setCellValueFactory(new PropertyValueFactory<>("FileType"));
      
        TableColumn<FileStats, Float> spamNameCol;
        spamNameCol = new TableColumn<>("Spam Probability");
        spamNameCol.setMinWidth(280);
        spamNameCol.setCellValueFactory(new PropertyValueFactory<>("SpamProbability"));
      
        // fill in the table
        table.getColumns().add(fileNameCol);
        table.getColumns().add(actualNameCol);
        table.getColumns().add(spamNameCol);
        table.setItems(DataSource.getAllStudents(wordCounter));   
        
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
        accuracyField.setPromptText("Accuracy");
        addArea.add(accuracyField, 1, 0);

        Label precisionLabel = new Label("Precision: ");
        addArea.add(precisionLabel, 0,2);

        TextField precisionField = new TextField();
        precisionField.setPromptText("Precision");
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