/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.PizzenDB.Pizza;
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

    /**
     * Initialize the controller with the pizza displayed in the KasseView Row and the Size of it (1-4 = small-family)
     *
     * @param pizza
     * @param size
     */
    public void init(Pizza pizza, int size) {

        this.kasseAnzahlLabel.setText(String.valueOf(1));
        anzahl = 1;
        switch (size) {
            //Small Pizza:
            case 1:
                this.kasseAnzahlName.setText(pizza.getName() + " (klein)");
                this.kassePreis.setText(pizza.getPreisKlein().get().toString() + "€");
                break;
            //Middle Pizza:
            case 2:
                this.kasseAnzahlName.setText(pizza.getName() + " (mittel)");
                this.kassePreis.setText(pizza.getPreisMittel().get().toString() + "€");
                break;
            //Big Pizza:
            case 3:
                this.kasseAnzahlName.setText(pizza.getName() + " (groß)");
                this.kassePreis.setText(pizza.getPreisGroß().get().toString() + "€");
                break;
            //Family Pizza:
            case 4:
                this.kasseAnzahlName.setText(pizza.getName() + " (Familie)");
                this.kassePreis.setText(pizza.getPreisFamilie().get().toString() + "€");
                break;
        }
    }

    /**
     * Increase the number of same Pizza Entries by One each time called
     */
    public void increaseAnzahl() {
        this.kasseAnzahlLabel.setText(String.valueOf(anzahl++));
    }

    /**
     * @return Label where the number of same Entries shown
     */
    public Label getKasseAnzahlLabel() {
        return kasseAnzahlLabel;
    }

    /**
     * @param kasseAnzahlLabel
     */
    public void setKasseAnzahlLabel(Label kasseAnzahlLabel) {
        this.kasseAnzahlLabel = kasseAnzahlLabel;
    }

    /**
     * @return Name of the Pizza Entry
     */
    public Label getKasseAnzahlName() {
        return kasseAnzahlName;
    }

    /**
     * @param kasseAnzahlName
     */
    public void setKasseAnzahlName(Label kasseAnzahlName) {
        this.kasseAnzahlName = kasseAnzahlName;
    }

    /**
     * @return Label where the Price of One Entity of the Pizza is shown
     */
    public Label getKassePreis() {
        return kassePreis;
    }

    /**
     * @param kassePreis
     */
    public void setKassePreis(Label kassePreis) {
        this.kassePreis = kassePreis;
    }

    /**
     * @return the Size of same Pizza Entries
     */
    public int getAnzahl() {
        return anzahl;
    }

    /** @param anzahl */
  public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
}
