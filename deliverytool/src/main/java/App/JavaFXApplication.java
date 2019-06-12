/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package App;

import Controller.WindowController;
import Model.Kasse.Registryadministration;
import Model.PizzenDB.Pizzavadministration;
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
    private static final String FXML_PATH = "Fxml/Window.fxml";

    //JavaFX scene
    protected Scene scene = null;

    WindowController controller;

    private Pizzavadministration verw;
    private Registryadministration verwk;

    /**
     * The main Method for the RestartMenuItem
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the Pizza- and Registryadministration
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();

        verw = new Pizzavadministration();
        verw.connectToDB();
        verwk = new Registryadministration();

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
        if (controller == null)
            controller = new WindowController();
        controller.setVerw(verw);
        controller.setVerwk(verwk);
        controller.loadFXMLItemsAgain();
        primaryStage.setTitle("Deliverytool");
        primaryStage.setScene(new Scene(loader.load()));
        controller.init(primaryStage);
        controller.show();

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

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public WindowController getController() {
        return controller;
    }

    public void setController(WindowController controller) {
        this.controller = controller;
    }

    public Pizzavadministration getVerw() {
        return verw;
    }

    public void setVerw(Pizzavadministration verw) {
        this.verw = verw;
    }

    public Registryadministration getVerwk() {
        return verwk;
    }

    public void setVerwk(Registryadministration verwk) {
        this.verwk = verwk;
    }
}
