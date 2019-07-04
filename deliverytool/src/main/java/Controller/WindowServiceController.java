/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Controller;

import Model.Kasse.NoSuchEntryException;
import Model.Kasse.OrderedPizza;
import Model.Kasse.Registryadministration;
import Model.Pizzen.Ingredient;
import Model.Pizzen.PizzaAdministration;
import Model.Pizzen.PizzaIngredientConnectionAdministration;
import Tools.LinkFetcher;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class WindowServiceController {

    @FXML
    private ListView<Pane> kasseListView;

    @FXML
    private ListView<Ingredient> zutatenListView;

    private static String FXML_WINDOWCONTROLLERSERVICE_FXML = "./deliverytool/Fxml/WindowService.fxml";

    private GridPane pane;
    private Registryadministration registerAdmininst;
    private PizzaIngredientConnectionAdministration pizzaIngredientConnectionAdministration;
    private PizzaAdministration pizzaAdministration;

    public WindowServiceController() throws IOException {
        registerAdmininst = Registryadministration.getInstance();
        pizzaAdministration = PizzaAdministration.getInstance();
        pizzaIngredientConnectionAdministration = PizzaIngredientConnectionAdministration.getInstance();
        loadFXMLItemsAgain();
        addListener();
    }

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
                zutatenListView.getItems().clear();
                OrderedPizza pizza = null;
                try {
                    pizza = registerAdmininst.get(kasseListView.getSelectionModel().getSelectedIndex());
                } catch (NoSuchEntryException e) {
                    e.printStackTrace();
                }
                assert pizza != null;
                Set<Ingredient> ingrediencesByPizzaId = null;
                try {
                    ingrediencesByPizzaId = pizzaIngredientConnectionAdministration.getIngrediencesByPizzaId(pizza.getPizza().getId());
                } catch (NoSuchEntryException | PizzaIngredientConnectionAdministration.IdOutOfRangeException e) {
                    e.printStackTrace();
                }
                assert ingrediencesByPizzaId != null;
                for (Ingredient e : ingrediencesByPizzaId) {
                    zutatenListView.getItems().add(e);
                }

            }
        });

        kasseListView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        zutatenListView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource().equals(kasseListView)) {

                }
            }
        });
    }

    public void init(Stage primaryStage) throws IOException, NoSuchEntryException {
        final VBox root = (VBox) primaryStage.getScene().getRoot();
        final ObservableList<Node> children = root.getChildren();
        children.remove(1);
        root.getChildren().add(pane);
        for (OrderedPizza m : registerAdmininst.getRegisterEntriesUnique()) {
            addKasseListView(m);
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
