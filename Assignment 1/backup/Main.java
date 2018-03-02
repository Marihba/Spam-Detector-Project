package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.security.Key;

public class Main extends Application {

    private BorderPane layout;
    //private TableView table;
    @Override
    public void start(Stage primaryStage) throws Exception{

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
        statsMenu.getItems().add(newMenuItem);
        statsMenu.getItems().add(new SeparatorMenuItem());
        statsMenu.getItems().add(new MenuItem("Bar-Graph"));

        // setting up Menu Tabs at correct positions (first to last).
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(statsMenu);



        //layout of UI using borderpane
        layout = new BorderPane();
        layout.setTop(menuBar);
        //layout.setCenter(table);

        //Scene and stage setup
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
