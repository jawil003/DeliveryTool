/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.Kasse.OrderedPizza;
import Model.Kasse.RegisterEntry;
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

    private static String FXML_WINDOWCONTROLLERSERVICE_FXML = "./deliverytool/Fxml/WindowService.fxml";

    private GridPane pane;
    private Registryadministration registerAdmininst;

    public void loadFXMLItemsAgain() throws IOException {
        FXMLLoader loader = new FXMLLoader(new File(LinkFetcher.normalizePath(FXML_WINDOWCONTROLLERSERVICE_FXML, "/deliverytool")).toURI().toURL());
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
        for (RegisterEntry m : e.getKassenEintraege()) {
            if (m instanceof OrderedPizza)
                addKasseListView((OrderedPizza) m);
        }
        primaryStage.show();

    }

    private void addKasseListView(OrderedPizza e) throws IOException {
        RowRegisterController c = new RowRegisterController();
        FXMLLoader loader = new FXMLLoader(new File(WindowController.getFxmlCellsRowKasseListcellFxml()).toURI().toURL());
        loader.setController(c);
        final Pane load = loader.load();
        switch (e.getGroeße()) {
            case Small:
                c.init(new Pizza(e.getName(), e.getPreis(), 0.0, 0.0, 0.0), PizzaSize.Small);
                break;
            case Middle:
                c.init(new Pizza(e.getName(), 0.0, e.getPreis(), 0.0, 0.0), PizzaSize.Middle);
                break;
            case Big:
                c.init(new Pizza(e.getName(), 0.0, 0.0, e.getPreis(), 0.0), PizzaSize.Big);
                break;
            case Family:
                c.init(new Pizza(e.getName(), 0.0, 0.0, 0.0, e.getPreis()), PizzaSize.Family);
                break;
        }

        kasseListView.getItems().add(load);
        kasseListView.refresh();
    }


}
