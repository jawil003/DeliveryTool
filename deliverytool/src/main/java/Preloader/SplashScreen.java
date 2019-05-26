/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Preloader;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class SplashScreen extends Preloader {

    private Stage splashScreen;
    private Pane root;

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
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
