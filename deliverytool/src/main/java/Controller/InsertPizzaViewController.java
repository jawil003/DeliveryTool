/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.PizzenDB.Ingredient;
import Model.PizzenDB.Ingredientsadministration;
import Model.PizzenDB.Pizza;
import Tools.LinkFetcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

public class InsertPizzaViewController extends Pizza {

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
        for (int i = 0; i < ingredientsadministration.getSize(); i++) {
            final Ingredient ingredient = ingredientsadministration.get(i);
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

    public void loadDBEntries() {

    }

    void init(Stage parent) throws IOException {
        addListener();
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
}
