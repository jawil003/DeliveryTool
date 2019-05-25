package Controller;

import Model.PizzenDB.Pizza;
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
    private TextField preisGrossField;

    @FXML
    private TextField preisFamilieField;

    @FXML
    private ListView<?> zutatenView;

    @FXML
    private ListView<?> hinzugefuegteZutatenView;

    @FXML
    private Button abbrechenButton;

    @FXML
    private Button okButton;


    private Pizza pizza;

    private Stage current;

    InsertPizzaViewController(Pizza pizza) {
        this.pizza = pizza;
    }

    void init(FXMLLoader loader, Stage parent) throws IOException {
        final Pane load = loader.load();
        current = new Stage();
        current.initOwner(parent);
        current.initModality(Modality.WINDOW_MODAL);
        final Scene scene = new Scene(load);
        current.setScene(scene);
        current.centerOnScreen();
        current.setResizable(false);
        current.show();

        abbrechenButton.setOnAction(event -> current.close());

        nameField.setOnKeyPressed(new enterPressedTextFieldListener(preisKleinFiled));

        preisKleinFiled.setOnKeyPressed(new enterPressedTextFieldListener(preisMittelField));

        preisMittelField.setOnKeyPressed(new enterPressedTextFieldListener(preisGrossField));

        preisGrossField.setOnKeyPressed(new enterPressedTextFieldListener(preisFamilieField));


    }

    TextField getNameField() {
        return nameField;
    }

    TextField getPreisKleinFiled() {
        return preisKleinFiled;
    }

    TextField getPreisMittelField() {
        return preisMittelField;
    }

    TextField getPreisGrossField() {
        return preisGrossField;
    }

    TextField getPreisFamilieField() {
        return preisFamilieField;
    }

    public void setZutatenView(ListView<?> zutatenView) {
        this.zutatenView = zutatenView;
    }

    public ListView<?> getHinzugefuegteZutatenView() {
        return hinzugefuegteZutatenView;
    }

    public void setHinzugefuegteZutatenView(ListView<?> hinzugefuegteZutatenView) {
        this.hinzugefuegteZutatenView = hinzugefuegteZutatenView;
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

    void close() {
        current.close();
    }

    private class enterPressedTextFieldListener implements EventHandler<KeyEvent> {
        TextField then;

        enterPressedTextFieldListener(TextField then) {
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
