import Controller.WindowController;
import Model.Pizzaverwaltung;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JavaFXApplication extends Application {

    //path to window fxml file
    protected static final String FXML_PATH = "Fxml/Window.fxml";

    //JavaFX scene
    protected Scene scene = null;

    //JavaFX root pane (AnchorPane)
    protected Pane rootPane = null;
    //JavaFX window controller (MVC principle)
    protected WindowController controller = null;
    private Pizzaverwaltung verw;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH).toURI().toURL());
        try {
            verw = new Pizzaverwaltung();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.controller = new WindowController(verw.getPizzen());
        loader.setController(this.controller);
        Parent rootPane = loader.load();
        primaryStage.setTitle("Deliverytool");
        this.scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        controller.init(primaryStage, scene, rootPane);
        primaryStage.centerOnScreen();
        primaryStage.requestFocus();
        primaryStage.show();
    }
}
