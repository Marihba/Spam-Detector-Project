package Sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;


public class Main extends Application {

    // instances
    private TableView<StudentRecord> table;
    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Spam Master 3000");

    // Creating table
        table = new TableView<>();
        table.setItems(DataSource.getAllStudents());
        table.setEditable(true);            // check with false after

     // Creating table columns
        TableColumn<StudentRecord, String> fileNameCol;
        fileNameCol = new TableColumn<>("File");
        fileNameCol.setStyle("-fx-Alignment: Center");
        fileNameCol.setMinWidth(300);
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("File"));
        fileNameCol.setCellFactory(TextFieldTableCell.<StudentRecord>forTableColumn());
        fileNameCol.setOnEditCommit((TableColumn.CellEditEvent<StudentRecord, String> event) -> {
            ((StudentRecord)event.getTableView().getItems().get(event.getTablePosition().getRow())).
                    setSid(event.getNewValue());
        });

        TableColumn<StudentRecord, Float> actualNameCol;
        actualNameCol = new TableColumn<>("Actual Class");
        actualNameCol.setStyle("-fx-Alignment: Center");
        actualNameCol.setMinWidth(100);
        actualNameCol.setCellValueFactory(new PropertyValueFactory<>("Actual Class"));

        TableColumn<StudentRecord, Float> spamNameCol;
        spamNameCol = new TableColumn<>("Spam Probability");
        spamNameCol.setStyle("-fx-Alignment: Center");
        spamNameCol.setMinWidth(280);
        spamNameCol.setCellValueFactory(new PropertyValueFactory<>("Spam Probability"));

    // fill in the table
        table.getColumns().add(fileNameCol);
        table.getColumns().add(actualNameCol);
        table.getColumns().add(spamNameCol);


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
        layout.setCenter(table);
        layout.setBottom(addArea);

        Scene scene = new Scene(layout, 685, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}



