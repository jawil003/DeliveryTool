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

    @Override
    public void init() throws Exception {
        super.init();
        FXMLLoader load = new FXMLLoader(new File("deliverytool/Fxml/SplashScreen.fxml").toURI().toURL());
        load.setController(this);
        root = load.load();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(root));
        splashScreen = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            splashScreen.hide();
        }
    }
}
