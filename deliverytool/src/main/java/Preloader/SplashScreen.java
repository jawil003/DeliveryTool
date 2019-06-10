/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Preloader;

import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class SplashScreen extends Preloader {

    private Stage splashScreen;
    private Pane root;

    @FXML
    private Label versionLabel;

    /**
     * Start method called within the Main Class
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(new Scene(root));
        splashScreen = primaryStage;
        java.util.Properties p = new Properties();
        versionLabel.setText("v." + model.getVersion());
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
