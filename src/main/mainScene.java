package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class mainScene {

    private static mainScene mainScene;
    private static Scene scene;

    /*The only thing that needs to be called to create the mainwindowView
     */
    private mainScene(Stage stage) {

        createScene(stage);

    }

    //this method is the entry point of the class to make shure that it is only called one time and not again
    public static void showScene(Stage stage) {
        if (mainScene != null) {

        } else {
            mainScene = new mainScene(stage);
        }
    }

    public Scene getScene() {
        return scene;
    }

    private void createScene(Stage stage) {
        Scene scene = new Scene(createPane());
        this.scene = scene;
        stage.setScene(scene);
        stage.show();
    }

    private Pane createPane() {

        BorderPane mainPane = new BorderPane();
        GridPane innerPane = new GridPane();
        innerPane.setAlignment(Pos.CENTER);
        innerPane.setVgap(10.0);
        innerPane.setHgap(10.0);
        innerPane.setPadding(new Insets(10.0));


        mainPane.setCenter(innerPane);

        setMenueBar(mainPane);
        setControls(innerPane);

        return mainPane;
    }

    private void setMenueBar(BorderPane pane) {
        MenuBar bar = new MenuBar();
        Menu datei = new Menu("Datei");
        MenuItem schliessen = new MenuItem();

        datei.getItems().add(schliessen);
        bar.getMenus().add(datei);

        pane.setTop(bar);
    }

    private void setControls(GridPane p) {
        ListView möglichePizzenListView = new ListView<>();
        ListView bestellungenListView = new ListView<>();
        //TODO Costomise ListView Cell to show name and prive in a row
        p.add(möglichePizzenListView, 0, 0, 3, 3);
        p.add(bestellungenListView, 4, 0, 3, 3);
    }
}
