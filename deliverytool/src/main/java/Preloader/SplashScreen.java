package Preloader;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class SplashScreen extends Preloader {

    private Stage splashScreen;
    private Pane root;

    /**
     * Initalize the Splashscreen
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        FXMLLoader load = new FXMLLoader(new File("deliverytool/Fxml/SplashScreen.fxml").toURI().toURL());
        load.setController(this);
        root = load.load();

    }

    /**
     * Start method called within the Main Class
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(new Scene(root));
        splashScreen = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
