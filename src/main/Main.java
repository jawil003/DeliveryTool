package main;

import javafx.application.Application;
import javafx.stage.Stage;
import toolClasses.Pizzaverwaltung;

public class Main extends Application {

    private Pizzaverwaltung pizzaverwaltung;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Pizzageschäft");
        mainScene.showScene(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
