package Controller;

import Model.PizzenDB.Pizza;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InsertPizzaViewController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField preisKleinFiled;

    @FXML
    private TextField preisMittelField;

    @FXML
    private TextField preisGroßField;

    @FXML
    private TextField preisFamilieField;

    @FXML
    private ListView<?> zutatenView;

    @FXML
    private ListView<?> hinzugefügteZutatenView;

    @FXML
    private Button abbrechenButton;

    @FXML
    private Button okButton;


    private Pizza pizza;

    Stage current;

    public InsertPizzaViewController(Pizza pizza) {
        this.pizza = pizza;
    }

    public void init(FXMLLoader loader, Stage parent) throws IOException {
        final Pane load = loader.load();
        current = new Stage();
        current.initOwner(parent);
        current.initModality(Modality.WINDOW_MODAL);
        final Scene scene = new Scene(load);
        current.setScene(scene);
        current.centerOnScreen();
        current.setResizable(false);
        current.show();

        abbrechenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                current.close();
            }
        });

        nameField.setOnKeyPressed(new enterPressedTextFieldListener(preisKleinFiled));

        preisKleinFiled.setOnKeyPressed(new enterPressedTextFieldListener(preisMittelField));

        preisMittelField.setOnKeyPressed(new enterPressedTextFieldListener(preisGroßField));

        preisGroßField.setOnKeyPressed(new enterPressedTextFieldListener(preisFamilieField));


    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public TextField getPreisKleinFiled() {
        return preisKleinFiled;
    }

    public void setPreisKleinFiled(TextField preisKleinFiled) {
        this.preisKleinFiled = preisKleinFiled;
    }

    public TextField getPreisMittelField() {
        return preisMittelField;
    }

    public void setPreisMittelField(TextField preisMittelField) {
        this.preisMittelField = preisMittelField;
    }

    public TextField getPreisGroßField() {
        return preisGroßField;
    }

    public void setPreisGroßField(TextField preisGroßField) {
        this.preisGroßField = preisGroßField;
    }

    public TextField getPreisFamilieField() {
        return preisFamilieField;
    }

    public void setPreisFamilieField(TextField preisFamilieField) {
        this.preisFamilieField = preisFamilieField;
    }

    public ListView<?> getZutatenView() {
        return zutatenView;
    }

    public void setZutatenView(ListView<?> zutatenView) {
        this.zutatenView = zutatenView;
    }

    public ListView<?> getHinzugefügteZutatenView() {
        return hinzugefügteZutatenView;
    }

    public void setHinzugefügteZutatenView(ListView<?> hinzugefügteZutatenView) {
        this.hinzugefügteZutatenView = hinzugefügteZutatenView;
    }

    public Button getAbbrechenButton() {
        return abbrechenButton;
    }

    public void setAbbrechenButton(Button abbrechenButton) {
        this.abbrechenButton = abbrechenButton;
    }

    public Button getOkButton() {
        return okButton;
    }

    public void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void close() {
        current.close();
    }

    private class enterPressedTextFieldListener implements EventHandler<KeyEvent> {
        TextField then;

        public enterPressedTextFieldListener(TextField then) {
            this.then = then;
        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                then.requestFocus();
            }
        }
    }
}
