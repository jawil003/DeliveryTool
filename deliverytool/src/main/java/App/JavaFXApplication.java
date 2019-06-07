/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package App;

import Controller.WindowController;
import Model.Kasse.Kassenverwaltung;
import Model.PizzenDB.Pizzaverwaltung;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class JavaFXApplication extends Application {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    //path to window fxml file
    private static final String FXML_PATH = "deliverytool/Fxml/Window.fxml";

    //JavaFX scene
    protected Scene scene = null;

    private Pizzaverwaltung verw;
    private Kassenverwaltung verwk;

    /**
     * The main Method for the RestartMenuItem
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the Pizza- and Kassenverwaltung
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        try {
            verw = new Pizzaverwaltung();
            verwk = new Kassenverwaltung();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param primaryStage
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage)
            throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException {
        FXMLLoader loader = new FXMLLoader(new File(FXML_PATH).toURI().toURL());

        //MainWindow is build and controller added

        //JavaFX window controller (MVC principle)
        WindowController controller = new WindowController(verw, verwk, primaryStage);
        loader.setController(controller);
        Parent rootPane = loader.load();
        primaryStage.setTitle("Deliverytool");
        this.scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        controller.init(primaryStage, scene, rootPane);
        primaryStage.centerOnScreen();
        //primaryStage.setResizable(false);
        primaryStage.requestFocus();
        primaryStage.show();

        //triggered when mainwindow is tried to close with the x window button

        primaryStage.setOnCloseRequest(new closeRequestHandler());
    }

    /**
     * Called when the mainWindow is closed
     */
    private class closeRequestHandler implements EventHandler<WindowEvent> {

        @Override
        public void handle(WindowEvent event) {
            if (verw.getSqlConnection().isRunning()) {
                event.consume();
            }

        }
    }
}
