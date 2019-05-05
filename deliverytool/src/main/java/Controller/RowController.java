package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Pizza;

import java.net.URL;
import java.util.ResourceBundle;

public class RowController implements Initializable {

    @FXML
    private ImageView pizzaImageview;

    @FXML
    private Label pizzaLabel;

    @FXML
    private Button kleinButton;

    @FXML
    private Button mittelButton;

    @FXML
    private Button großButton;

    @FXML
    private Button familieButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(Pizza pizza) {
        // load the image
        pizzaImageview.setImage(new Image("pizza.png"));

        //set title and image (icon)
        this.pizzaLabel.setText(pizza.getName());
        this.kleinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

    }
}
