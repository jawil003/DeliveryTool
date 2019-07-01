package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class InsertNewIngredienceViewController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField mengeTextField;

    @FXML
    private RadioButton grammRadioButton;

    @FXML
    private RadioButton literRadioButton;

    @FXML
    private Button abbrechenButton;

    @FXML
    private Button okButton;

    private FXMLLoader loader;

    private Stage primaryStage; //off the Controller

    private Pane primaryPane;

    private Stage owner;


    public InsertNewIngredienceViewController(Stage stage) throws MalformedURLException {
        loadFXMLAgain();
        owner = stage;
    }

    private void loadFXMLAgain() throws MalformedURLException {
        loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewIngredienceView.fxml").toURI().toURL());

    }

    private void setController() {
        loader.setController(this);
    }

    public void init() throws IOException {
        if (loader.getController() == null)
            setController();
        primaryPane = loader.load();

        //groupRadioButtons();
        addListener();
        primaryStage = new Stage();
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(owner);
        primaryStage.setScene(new Scene(primaryPane));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void groupRadioButtons() {
        final ToggleGroup radioButtons = new ToggleGroup();
        grammRadioButton.setToggleGroup(radioButtons);
        grammRadioButton.setSelected(true);
        literRadioButton.setToggleGroup(radioButtons);
    }

    private void addListener() {
        abbrechenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public TextField getMengeTextField() {
        return mengeTextField;
    }

    public void setMengeTextField(TextField mengeTextField) {
        this.mengeTextField = mengeTextField;
    }

    public RadioButton getGrammRadioButton() {
        return grammRadioButton;
    }

    public void setGrammRadioButton(RadioButton grammRadioButton) {
        this.grammRadioButton = grammRadioButton;
    }

    public RadioButton getLiterRadioButton() {
        return literRadioButton;
    }

    public void setLiterRadioButton(RadioButton literRadioButton) {
        this.literRadioButton = literRadioButton;
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

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }
}
