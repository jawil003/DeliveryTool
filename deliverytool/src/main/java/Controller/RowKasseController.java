package Controller;

import Model.Pizza;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RowKasseController implements Initializable {

    @FXML
    private Label kasseAnzahlLabel;

    @FXML
    private Label kasseAnzahlName;

    @FXML
    private Label kassePreis;

    private int anzahl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void init(Pizza pizza, int size) {

        this.kasseAnzahlLabel.setText(String.valueOf(1));
        anzahl = 1;
        switch (size) {
            case 1:
                this.kasseAnzahlName.setText(pizza.getName() + " (klein)");
                this.kassePreis.setText(pizza.getPreisKlein().get().toString() + "0€");
                break;
            case 2:
                this.kasseAnzahlName.setText(pizza.getName() + " (mittel)");
                this.kassePreis.setText(pizza.getPreisMittel().get().toString() + "0€");
                break;
            case 3:
                this.kasseAnzahlName.setText(pizza.getName() + " (groß)");
                this.kassePreis.setText(pizza.getPreisGroß().get().toString() + "0€");
                break;
            case 4:
                this.kasseAnzahlName.setText(pizza.getName() + " (Familie)");
                this.kassePreis.setText(pizza.getPreisFamilie().get().toString() + "0€");
                break;
        }
    }

    public void increaseAnzahl() {
        this.kasseAnzahlLabel.setText(String.valueOf(anzahl++));
    }

    public Label getKasseAnzahlLabel() {
        return kasseAnzahlLabel;
    }

    public void setKasseAnzahlLabel(Label kasseAnzahlLabel) {
        this.kasseAnzahlLabel = kasseAnzahlLabel;
    }

    public Label getKasseAnzahlName() {
        return kasseAnzahlName;
    }

    public void setKasseAnzahlName(Label kasseAnzahlName) {
        this.kasseAnzahlName = kasseAnzahlName;
    }

    public Label getKassePreis() {
        return kassePreis;
    }

    public void setKassePreis(Label kassePreis) {
        this.kassePreis = kassePreis;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
}
