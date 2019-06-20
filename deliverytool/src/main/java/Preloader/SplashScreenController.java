/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Preloader;

import Tools.LinkFetcher;
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
import java.io.IOException;
import java.util.Properties;

public class SplashScreenController extends Preloader {

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
    public void start(Stage primaryStage) throws IOException {
        final String s = LinkFetcher.normalizePath("deliverytool/Fxml/SplashScreen.fxml", "/deliverytool");
        FXMLLoader loader = new FXMLLoader(new File(s).toURI().toURL());
        loader.setController(this);
        root = loader.load();
        primaryStage.setScene(new Scene(root));
        splashScreen = primaryStage;
        java.util.Properties p = new Properties();
        Model model = null;
        FileReader reader = null;
        MavenXpp3Reader mavenreader = new MavenXpp3Reader();

        try {
            reader = new FileReader(new File("deliverytool/pom.xml"));
            model = mavenreader.read(reader);
            model.setPomFile(new File("pom.xml"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        assert model != null;
        versionLabel.setText("v." + model.getVersion());
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            splashScreen.hide();
        }
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Label getVersionLabel() {
        return versionLabel;
    }

    public void setVersionLabel(Label versionLabel) {
        this.versionLabel = versionLabel;
    }
}
