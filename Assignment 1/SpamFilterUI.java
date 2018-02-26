
import java.awt.Color;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.*;
public class SpamFilterUI extends Application {
    //declaration and initiation of variables
    private TextField accuracyText = new TextField();
    private TextField precisionText = new TextField();
    private TextArea fileText = new TextArea();
    @Override
    public void start(Stage primaryStage) {
        //editing attributes of text field
        fileText.setEditable(false);
        fileText.setPrefHeight(400);
        fileText.setText("hello\n");
        fileText.appendText("Hello");
        precisionText.setText("number");
        accuracyText.setText("number");
        precisionText.setEditable(false);
        accuracyText.setEditable(false);
       
        //creating labels
        Label fileLabel = new Label("\t\tFile\t\t\t\t\t\t\tActual Class\t\t\t\t\t\tSpam Probability\t\t");
        Label accuracyLabel = new Label("Accuracy:");
        Label precisionLabel = new Label("Precision:"); 
        
        /*fileLabel.setStyle("-fx-border-color: black;");
        actualClassLabel.setStyle("-fx-border-color: black;");
        spamLabel.setStyle("-fx-border-color: black;");
        */        
        //creating vertical and horizontal boxes to store the different elements
        HBox accuracyHBox = new HBox(accuracyLabel, accuracyText);
        HBox precisionHBox = new HBox(precisionLabel, precisionText);
        VBox vbo = new VBox(fileLabel,fileText,accuracyHBox, precisionHBox);
       
        //creates the scene for the program
        Scene scene = new Scene(vbo, 600, 400);  
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
