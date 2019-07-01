/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.Kasse.OrderedPizza;
import Model.PizzenDB.Pizza;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RowRegisterController implements Initializable {

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

    public void init(Pizza pizza, PizzaSize size) {

        this.kasseAnzahlLabel.setText(String.valueOf(1));
        anzahl = 1;
        switch (size) {
            case Small:
                this.kasseAnzahlName.setText(pizza.getName() + " (Klein)");
                this.kassePreis.setText(String.format("%.2f", pizza.getSmallPrice()) + "€");
                break;
            case Middle:
                this.kasseAnzahlName.setText(pizza.getName() + " (Mittel)");
                this.kassePreis.setText(String.format("%.2f", pizza.getMiddlePrice()) + "€");
                break;
            case Big:
                this.kasseAnzahlName.setText(pizza.getName() + " (Groß)");
                this.kassePreis.setText(String.format("%.2f", pizza.getBigPrice()) + "€");
                break;
            case Family:
                this.kasseAnzahlName.setText(pizza.getName() + " (Familie)");
                this.kassePreis.setText(String.format("%.2f", pizza.getFamilyPrice()) + "€");
                break;
        }
    }

    public void init(OrderedPizza pizza) {
        switch (pizza.getGroeße()) {
            case Small:
                init(new Pizza(pizza.getName(), pizza.getPreis(), 0.0, 0.0, 0.0), pizza.getGroeße());
                break;
            case Middle:
                init(new Pizza(pizza.getName(), 0.0, pizza.getPreis(), 0.0, 0.0), pizza.getGroeße());
                break;
            case Big:
                init(new Pizza(pizza.getName(), 0.0, 0.0, pizza.getPreis(), 0.0), pizza.getGroeße());
                break;
            case Family:
                init(new Pizza(pizza.getName(), 0.0, 0.0, 0.0, pizza.getPreis()), pizza.getGroeße());
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
