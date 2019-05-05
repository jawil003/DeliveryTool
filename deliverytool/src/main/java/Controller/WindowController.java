package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Pizza;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class WindowController {

    protected static final String ROW_FXML = "deliverytool/fxml/row1.fxml";
    protected LinkedList<Pizza> list = null;
    @FXML
    private ListView<Pane> pizzenListview;
    @FXML
    private ListView<?> kasseListview;
    @FXML
    private MenuBar menuBar;

    public WindowController(LinkedList<Pizza> pizzen) {
        this.list = pizzen;

    }

    public void init(Stage primaryStage, Scene scene, Parent rootPane) {
        int counter = 0;

        //create rows
        for (Pizza pizza : this.list) {
            addRow(pizza, ROW_FXML);

            //increment counter
            counter++;
        }
    }

    protected void addRow(Pizza pizza, String fxmlPath) {
        // load fxml
        try {
            FXMLLoader loader = new FXMLLoader(new File(fxmlPath).toURI().toURL());

            //set controller
            RowController rowController = new RowController();
            loader.setController(rowController);

            Pane rootPane = loader.load();//FXMLLoader.load(new File(fxmlPath).toURI().toURL());

            //initialize tab controller
            rowController.init(pizza);

            this.pizzenListview.getItems().add(rootPane);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
