package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Pizzaverwaltung;

public class Main extends Application {

    private Pizzaverwaltung pizzaverwaltung;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("DeliveryTool");
        mainWindow.showScene(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
