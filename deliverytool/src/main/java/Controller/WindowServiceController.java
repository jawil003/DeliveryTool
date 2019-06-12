/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.Kasse.OrderedPizza;
import Model.Kasse.RegistryEntryWrapper;
import Model.Kasse.Registryadministration;
import Model.PizzenDB.Pizza;
import Tools.LinkFetcher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class WindowServiceController {

    @FXML
    private ListView<Pane> kasseListView;

    @FXML
    private ListView<Pane> zutatenListView;

    private GridPane pane;
    private Registryadministration registerAdmininst;

    public void loadFXMLItemsAgain() throws IOException {
        FXMLLoader loader = new FXMLLoader(new File(LinkFetcher.normalizePath("./deliverytool/Fxml/WindowService.fxml", "/deliverytool")).toURI().toURL());
        if (loader.getController() == null)
            loader.setController(this);
        pane = loader.load();

    }

    public void init(Stage primaryStage, Registryadministration e) throws IOException {
        this.registerAdmininst = e;
        final VBox root = (VBox) primaryStage.getScene().getRoot();
        final ObservableList<Node> children = root.getChildren();
        children.remove(1);
        root.getChildren().add(pane);
        for (RegistryEntryWrapper m : e.getKassenEintraege()) {
            addKasseListView(m.getE());
        }
        primaryStage.show();

    }

    private void addKasseListView(OrderedPizza e) throws IOException {
        RowRegisterController c = new RowRegisterController();
        FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/Cells/RowKasseListcell.fxml").toURI().toURL());
        loader.setController(c);
        final Pane load = loader.load();
        switch (e.getGroeße()) {
            case 'k':
                c.init(new Pizza(e.getName(), e.getPreis(), 0.0, 0.0, 0.0), 1);
                break;
            case 'm':
                c.init(new Pizza(e.getName(), 0.0, e.getPreis(), 0.0, 0.0), 2);
                break;
            case 'g':
                c.init(new Pizza(e.getName(), 0.0, 0.0, e.getPreis(), 0.0), 3);
                break;
            case 'f':
                c.init(new Pizza(e.getName(), 0.0, 0.0, 0.0, e.getPreis()), 4);
                break;
        }

        kasseListView.getItems().add(load);
        kasseListView.refresh();
    }
}
