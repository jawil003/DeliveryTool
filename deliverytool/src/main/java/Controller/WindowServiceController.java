/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.Kasse.NoSuchEntryException;
import Model.Kasse.OrderedPizza;
import Model.Kasse.RegisterEntry;
import Model.Kasse.Registryadministration;
import Model.PizzenDB.Ingredient;
import Model.PizzenDB.Pizza;
import Model.PizzenDB.Pizzavadministration;
import Tools.LinkFetcher;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class WindowServiceController {

    @FXML
    private ListView<Pane> kasseListView;

    @FXML
    private ListView<Ingredient> zutatenListView;

    private static String FXML_WINDOWCONTROLLERSERVICE_FXML = "./deliverytool/Fxml/WindowService.fxml";

    private GridPane pane;
    private Registryadministration registerAdmininst;
    private Pizzavadministration pizzavadministration;

    public void loadFXMLItemsAgain() throws IOException {
        FXMLLoader loader = new FXMLLoader(new File(LinkFetcher.normalizePath(FXML_WINDOWCONTROLLERSERVICE_FXML, "/deliverytool")).toURI().toURL());
        if (loader.getController() == null)
            loader.setController(this);
        pane = loader.load();

    }

    private void addListener() {
        kasseListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                assert (kasseListView.isFocused());
                if (event.getClickCount() >= 2) {
                    final Pane selectedItem = kasseListView.getSelectionModel().getSelectedItem();
                    Pizza search = null;
                    try {
                        search = pizzavadministration.search(((Label) selectedItem.lookup("#kasseAnzahlName")).getText());
                    } catch (NoSuchEntryException e) {
                        e.printStackTrace();
                    }

                    assert search != null;
                    LinkedList<Ingredient> ingredients = search.getIngridience();

                    for (Ingredient e : ingredients) {
                        zutatenListView.getItems().add(e);
                    }


                }
            }
        });
    }

    public void init(Stage primaryStage, Registryadministration e, Pizzavadministration administration) throws IOException, NoSuchEntryException {
        this.registerAdmininst = e;
        this.pizzavadministration = pizzavadministration;
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

    private void addKasseListView(OrderedPizza e) throws IOException, NoSuchEntryException {
        RowRegisterController c = new RowRegisterController();
        FXMLLoader loader = new FXMLLoader(new File(WindowController.getFxmlCellsRowKasseListcellFxml()).toURI().toURL());
        loader.setController(c);
        final Pane load = loader.load();
        c.init(e);
        c.getKasseAnzahlLabel().setText(String.valueOf(registerAdmininst.getSize(e)));
        kasseListView.getItems().add(load);
        kasseListView.refresh();
    }


}
