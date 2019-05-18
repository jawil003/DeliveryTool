package App;

import Controller.WindowController;
import Model.Kasse.Kassenverwaltung;
import Model.PizzenDB.Pizzaverwaltung;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class JavaFXApplication extends Application {

    //path to window fxml file
    private static final String FXML_PATH = "deliverytool/Fxml/Window.fxml";

    //JavaFX scene
    protected Scene scene = null;

    //JavaFX window controller (MVC principle)
    private WindowController controller;
    private Pizzaverwaltung verw;
    private Kassenverwaltung verwk;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new File(FXML_PATH).toURI().toURL());
        try {
            verw = new Pizzaverwaltung();
            verwk = new Kassenverwaltung();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.controller = new WindowController(verw, verwk, primaryStage);
        loader.setController(this.controller);
        Parent rootPane = loader.load();
        primaryStage.setTitle("Deliverytool");
        this.scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        controller.init(primaryStage, scene, rootPane);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.requestFocus();
        primaryStage.show();
    }
}
