/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import Model.PizzenDB.Pizza;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RowPizzenController implements Initializable {

    @FXML
    private ImageView pizzaImageview;

    @FXML
    private Label pizzaLabel;

    @FXML
    private Button kleinButton;

    @FXML
    private Button mittelButton;

    @FXML
    private Button grossButton;

    @FXML
    private Button familieButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(Pizza pizza) {
        // load the image
        pizzaImageview.setImage(new Image("Classdependencies/Window/PizzaListViewImg.png"));

        //set title and image (icon)
        this.pizzaLabel.setText(pizza.getName());
        this.kleinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        this.mittelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        this.grossButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        this.familieButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

    }

    public ImageView getPizzaImageview() {
        return pizzaImageview;
    }

    public void setPizzaImageview(ImageView pizzaImageview) {
        this.pizzaImageview = pizzaImageview;
    }

    public Label getPizzaLabel() {
        return pizzaLabel;
    }

    public void setPizzaLabel(Label pizzaLabel) {
        this.pizzaLabel = pizzaLabel;
    }

    public Button getKleinButton() {
        return kleinButton;
    }

    public void setKleinButton(Button kleinButton) {
        this.kleinButton = kleinButton;
    }

    public Button getMittelButton() {
        return mittelButton;
    }

    public void setMittelButton(Button mittelButton) {
        this.mittelButton = mittelButton;
    }

    public Button getGrossButton() {
        return grossButton;
    }

    public void setGrossButton(Button grossButton) {
        this.grossButton = grossButton;
    }

    public Button getFamilieButton() {
        return familieButton;
    }

    public void setFamilieButton(Button familieButton) {
        this.familieButton = familieButton;
    }
}
