package Controller;

import Model.PizzenDB.Pizza;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RowPizzenController implements Initializable {

    /**
     * @author Jannik Will
     * @version 1.0
     */

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

    /**
     * Initialize a new Row in the PizzaListView
     *
     * @param pizza
     */
    public void init(Pizza pizza) {
        // load the image
        pizzaImageview.setImage(new Image("pizza.png"));

        //set title and image (icon)
        this.pizzaLabel.setText(pizza.getName());

    }

    /**
     * @return the ImageView on the left Side of the row
     */
    public ImageView getPizzaImageview() {
        return pizzaImageview;
    }

    /**
     * @param pizzaImageview
     */
    public void setPizzaImageview(ImageView pizzaImageview) {
        this.pizzaImageview = pizzaImageview;
    }

    /**
     * @return the Label where the name of the Pizza is shown
     */
    public Label getPizzaLabel() {
        return pizzaLabel;
    }

    /**
     * @param pizzaLabel
     */
    public void setPizzaLabel(Label pizzaLabel) {
        this.pizzaLabel = pizzaLabel;
    }

    /**
     * @return the button where a small Pizza is toggled
     */
    public Button getKleinButton() {
        return kleinButton;
    }

    /**
     * @param kleinButton
     */
    public void setKleinButton(Button kleinButton) {
        this.kleinButton = kleinButton;
    }

    /**
     * @return the button where a middle Pizza is toggled
     */
    public Button getMittelButton() {
        return mittelButton;
    }

    /**
     * @param mittelButton
     */
    public void setMittelButton(Button mittelButton) {
        this.mittelButton = mittelButton;
    }

    /**
     * @return the button where a big Pizza is toggled
     */
    public Button getGrossButton() {
        return grossButton;
    }

    /**
     * @param grossButton
     */
    public void setGrossButton(Button grossButton) {
        this.grossButton = grossButton;
    }

    /**
     * @return the button where a family Pizza is toggled
     */
    public Button getFamilieButton() {
        return familieButton;
    }

    /**
     * @param familieButton
     */
    public void setFamilieButton(Button familieButton) {
        this.familieButton = familieButton;
    }
}
