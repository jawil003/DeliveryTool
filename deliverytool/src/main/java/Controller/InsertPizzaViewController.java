/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Controller;

import Model.Kasse.NoSuchEntryException;
import Model.Pizzen.Ingredient;
import Model.Pizzen.Ingredientsadministration;
import Model.Pizzen.Pizza;
import Tools.LinkFetcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class InsertPizzaViewController {

    Pane root;

    @FXML
    private TextField nameField;

    @FXML
    private TextField preisKleinField;

    @FXML
    private TextField preisMittelField;

    @FXML
    private TextField preisGrossField;

    @FXML
    private TextField preisFamilieField;

    @FXML
    private ListView<Ingredient> zutatenView;

    @FXML
    private ListView<Ingredient> hinzugefuegteZutatenView;

    @FXML
    private Button abbrechenButton;

    @FXML
    private Button okButton;


    private Pizza pizza;

    private Stage current;

    private Ingredientsadministration ingredientsadministration;

    /**
     * @param pizza
     */
    InsertPizzaViewController(Pizza pizza, Ingredientsadministration ingredientsadministration) {

        this.pizza = pizza;
        this.ingredientsadministration = ingredientsadministration;
    }

    public void loadFXMLItemsAgain() throws IOException {
        final String s = LinkFetcher.normalizePath(Paths.get("deliverytool/Fxml/InsertNewPizzaView.fxml").toAbsolutePath().toString(), "/deliverytool");
        FXMLLoader loader = new FXMLLoader(new File(s).toURI().toURL());
        if (loader.getController() == null)
            loader.setController(this);
        root = loader.load();
    }

    private void loadIngrediences() {
        for (Ingredient ingredient : ingredientsadministration.getList()) {
            addZutat(ingredient);

        }
    }

    private void addListener() {
        zutatenView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {

                }
            }
        });
    }

    void init(Stage parent) throws IOException, NoSuchEntryException {
        addListener();
        zutatenView.setCellFactory(param -> new IngredientListCell());
        current = new Stage();
        current.setTitle("Pizza hinzufügen");
        current.initOwner(parent);
        current.initModality(Modality.WINDOW_MODAL);
        loadIngrediences();
        final Scene scene = new Scene(root);
        current.setScene(scene);
        current.centerOnScreen();
        current.setResizable(false);
        current.show();

        abbrechenButton.setOnAction(event -> current.close());

        nameField.setOnKeyPressed(new enterPressedTextFieldListener(preisKleinField));

        preisKleinField.setOnKeyPressed(new enterPressedTextFieldListener(preisMittelField));

        preisMittelField.setOnKeyPressed(new enterPressedTextFieldListener(preisGrossField));

        preisGrossField.setOnKeyPressed(new enterPressedTextFieldListener(preisFamilieField));


    }

    private void addZutat(Ingredient e) {
        zutatenView.getItems().add(e);

    }

    private void addHinzugefuegteZutat(Ingredient e) {
        hinzugefuegteZutatenView.getItems().add(e);
    }

    /**
     * @return the TextField where the Pizzaname is entered
     */
    TextField getNameField() {
        return nameField;
    }

    /**
     * @return the TextField where the Price of the small Pizza is entered
     */
    TextField getPreisKleinField() {
        return preisKleinField;
    }

    /**
     * @return the TextField where the Price of the middle Pizza is entered
     */
    TextField getPreisMittelField() {
        return preisMittelField;
    }

    /** @return the TextField where the Price of the big Pizza is entered */
    TextField getPreisGrossField() {
        return preisGrossField;
    }

    /** @return the TextField where the Price of the family Pizza is entered */
    TextField getPreisFamilieField() {
        return preisFamilieField;
    }

    /**
     * @param zutatenView
     */
    public void setZutatenView(ListView<Ingredient> zutatenView) {
        this.zutatenView = zutatenView;
    }

    public ListView<?> getZutatenView() {
        return this.zutatenView;
    }

    /**
     * @return the ListView where the Zutatten are shown which are added to the Pizza
     */
    public ListView<?> getHinzugefuegteZutatenView() {
        return hinzugefuegteZutatenView;
    }

    /**
     * @param hinzugefuegteZutatenView
     */
    public void setHinzugefuegteZutatenView(ListView<Ingredient> hinzugefuegteZutatenView) {
        this.hinzugefuegteZutatenView = hinzugefuegteZutatenView;
    }

    /** @return the button where the window is closed without adding the entry */
    public Button getAbbrechenButton() {
        return abbrechenButton;
    }

    /** @param abbrechenButton */
    public void setAbbrechenButton(Button abbrechenButton) {
        this.abbrechenButton = abbrechenButton;
    }

    public Button getOkButton() {
        return okButton;
    }

    /** @param okButton */
    public void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    /** @return the pizza which is entered by the underlying mask/view */
    public Pizza getPizza() {
        return pizza;
    }

    /** @param pizza */
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }


    void close() {
        current.close();
    }

    private class enterPressedTextFieldListener implements EventHandler<KeyEvent> {
        TextField then;

        enterPressedTextFieldListener(TextField then) {
            this.then = then;
        }

        /** @param event */
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                then.requestFocus();
            }
        }
    }

    /**
     * @todo Create Drag and Drop in Pizza Adding VieW
     * @body Create Drag and Drop in Pizza Adding VieW for Ingredients to add them to the Pizza
     */
    private class IngredientListCell extends ListCell<Ingredient> {
        public IngredientListCell() {
            super();
            setContentDisplay(ContentDisplay.CENTER);
            setAlignment(Pos.CENTER);

            setOnDragDetected(event -> {
                if (getItem() == null) {
                    return;
                }
                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(getItem().getName());


                dragboard.setContent(content);

                event.consume();
                hinzugefuegteZutatenView.getItems().add(getItem());

            });
        }

        @Override
        protected void updateItem(Ingredient item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(item.getName());
            }
        }
    }
}

