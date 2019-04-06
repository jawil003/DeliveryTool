package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Pizza;
import model.Pizzaverwaltung;

public class mainWindow {

    private static mainWindow mainWindow;
    private static Scene scene;
    private ListView möglichePizzenListView;
    private Pizzaverwaltung pizzaverwaltung;

    /*The only thing that needs to be called to create the mainwindowView
     */
    private mainWindow(Stage stage) {

        createScene(stage);

    }

    //this method is the entry point of the class to make shure that it is only called one time and not again
    public static mainWindow showScene(Stage stage) {
        if (mainWindow != null) {

        } else {
            mainWindow = new mainWindow(stage);
        }

        return mainWindow;
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

    public static main.mainWindow getMainWindow() {
        return mainWindow;
    }

    public static void setMainWindow(main.mainWindow mainWindow) {
        main.mainWindow.mainWindow = mainWindow;
    }

    public static void setScene(Scene scene) {
        main.mainWindow.scene = scene;
    }

    private void setControls(GridPane p) {
        möglichePizzenListView = new ListView<>();
        for (Pizza e : pizzaverwaltung) {
            möglichePizzenListView.getItems().add(e);
        }

        möglichePizzenListView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCell();
            }
        });
        Label pizzenLabel = new Label("Pizzen");

        Button plusButton = new Button();
        plusButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/plus.png"))));
        pizzenLabel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pizza.png"))));
        Label bestellungenLabel = new Label("Bestellung(en)");
        ListView bestellungenListView = new ListView<>();
        bestellungenLabel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cash-register.png"))));
        Label gesamtPreis = new Label("Gesamt:");
        //TODO Customise ListView Cell to show name and price in a row
        p.add(pizzenLabel, 0, 0);
        //p.add(plusLabel, 3, 0);
        p.add(bestellungenLabel, 4, 0);
        p.add(möglichePizzenListView, 0, 1, 3, 3);
        p.add(bestellungenListView, 4, 1, 3, 3);
        p.add(gesamtPreis, 4, 4);
    }

    public ListView getMöglichePizzenListView() {
        return möglichePizzenListView;
    }

    public void setMöglichePizzenListView(ListView möglichePizzenListView) {
        this.möglichePizzenListView = möglichePizzenListView;
    }

    public Pizzaverwaltung getPizzaverwaltung() {
        return pizzaverwaltung;
    }

    public void setPizzaverwaltung(Pizzaverwaltung pizzaverwaltung) {
        this.pizzaverwaltung = pizzaverwaltung;
    }
}
