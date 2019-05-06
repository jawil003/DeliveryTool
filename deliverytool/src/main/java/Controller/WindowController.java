package Controller;

import Model.Pizza;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class WindowController {

  protected static final String ROW_FXML = "deliverytool/fxml/RowPizzenListview.fxml";
  protected static final String ROW2_FXML = "deliverytool/fxml/RowKasseListview.fxml";
  protected LinkedList<Pizza> list = null;
  @FXML
  private ListView<Pane> pizzenListview;
  @FXML
  private ListView<Pane> kasseListview;
  @FXML
  private MenuBar menuBar;

  public WindowController(LinkedList<Pizza> pizzen) {
    this.list = pizzen;
  }

  public static String getRowFxml() {
    return ROW_FXML;
  }

  public static String getRow2Fxml() {
    return ROW2_FXML;
  }

  public void init(Stage primaryStage, Scene scene, Parent rootPane) {
    int counter = 0;

    // create rows
    for (Pizza pizza : this.list) {
      addRow(pizza, ROW_FXML);
      try {
        addKasseneintrag(pizza, 1);
      } catch (IOException e) {
        e.printStackTrace();
      }

      // increment counter
      counter++;
    }
  }

  protected void addRow(Pizza pizza, String fxmlPath) {
    // load fxml
    try {
      FXMLLoader loader = new FXMLLoader(new File(fxmlPath).toURI().toURL());

      // set controller
      RowPizzenController rowController = new RowPizzenController();
      loader.setController(rowController);

      Pane rootPane = loader.load();

      // initialize tab controller
      rowController.init(pizza);

      this.pizzenListview.getItems().add(rootPane);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public void addKasseneintrag(Pizza pizza, int size) throws IOException {
    FXMLLoader loader = new FXMLLoader(new File(ROW2_FXML).toURI().toURL());
    RowKasseController rowController = new RowKasseController();
    loader.setController(rowController);

    Pane rootPane = loader.load();

    rowController.init(pizza, size);

    this.kasseListview.getItems().add(rootPane);

  }

  public LinkedList<Pizza> getList() {
    return list;
  }

  public void setList(LinkedList<Pizza> list) {
    this.list = list;
  }

  public ListView<Pane> getPizzenListview() {
    return pizzenListview;
  }

  public void setPizzenListview(ListView<Pane> pizzenListview) {
    this.pizzenListview = pizzenListview;
  }

  public ListView<Pane> getKasseListview() {
    return kasseListview;
  }

  public void setKasseListview(ListView<Pane> kasseListview) {
    this.kasseListview = kasseListview;
  }

  public MenuBar getMenuBar() {
    return menuBar;
  }

  public void setMenuBar(MenuBar menuBar) {
    this.menuBar = menuBar;
  }
}
