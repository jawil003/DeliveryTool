package Controller;

import App.JavaFXApplication;
import Model.Pizza;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class WindowController {

  protected static final String ROW_FXML = "deliverytool/Fxml/Cells/RowPizzenListcell.fxml";
  protected static final String ROW2_FXML = "deliverytool/Fxml/Cells/RowKasseListcell.fxml";
  protected LinkedList<Pizza> list = null;
  RowPizzenController pizzenContr;
  RowKasseController kasseContr;
  @FXML
  private ListView<Pane> pizzenListview;
  @FXML
  private ListView<Pane> kasseListview;
  @FXML
  private MenuBar menuBar;
  @FXML
  private MenuItem ausgewähltLoeschen;

  @FXML
  private MenuItem allesLoeschenItem;

  @FXML
  private MenuItem eintragHinzufuegenItem;

  @FXML
  private MenuItem ueberItem;
    @FXML
    private MenuItem schließenItem;
    @FXML
    private MenuItem neustartItem;

  double gesamterPreis = 0.00;
  @FXML
  private Label gesamterPreisLabel;
  private Stage primaryStage;

  public WindowController(LinkedList<Pizza> pizzen, Stage primaryStage) {

    this.list = pizzen;
    this.primaryStage = primaryStage;
  }

  public static String getRowFxml() {
    return ROW_FXML;
  }

  public static String getRow2Fxml() {
    return ROW2_FXML;
  }

  public void init(Stage primaryStage, Scene scene, Parent rootPane) {

    kasseListview.getItems().addListener(new ListChangeListener<Pane>() {
      @Override

      //TODO: Change it so that only the changed elements are called

      public void onChanged(Change<? extends Pane> c) {
        while (c.next()) {
          for (Pane additem : c.getAddedSubList()) {
            final Label lookup = (Label) additem.lookup("#kassePreis");
            gesamterPreis += Double.valueOf(lookup.getText().substring(0, lookup.getText().length() - 1));
          }
        }
        gesamterPreisLabel.setText(String.format("%s0", String.valueOf(gesamterPreis)) + "€");
      }
    });

    // create rows
    for (Pizza pizza : this.list) {
      addPizzaRow(pizza, ROW_FXML);

      //Actions:

        schließenItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

      //FIXME: NeustartItem causes a loop and freeze of gui

        neustartItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                      Platform.exit();
                      try {
                        new JavaFXApplication().start(new Stage());
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }
                });
            }
        });

      eintragHinzufuegenItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            eintragHinzufuegen();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

      pizzenContr.getKleinButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 1);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

        pizzenContr.getMittelButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 2);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

        pizzenContr.getGrossButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 3);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

        pizzenContr.getFamilieButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 4);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  //A Pizza is added

  private void addPizzaRow(Pizza pizza, String fxmlPath) {
    // load fxml
    try {
      FXMLLoader loader = new FXMLLoader(new File(fxmlPath).toURI().toURL());

      // set controller
      pizzenContr = new RowPizzenController();
      loader.setController(pizzenContr);

      Pane rootPane = loader.load();

      // initialize tab controller
      pizzenContr.init(pizza);

      this.pizzenListview.getItems().add(rootPane);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void addKasseneintrag(Pizza pizza, int size) throws IOException {
    FXMLLoader loader = new FXMLLoader(new File(ROW2_FXML).toURI().toURL());
    for (int i = 0; i < kasseListview.getItems().size(); i++) {
      Pane p = kasseListview.getItems().get(i);
      Label kasseAnzahlLabel = (Label) kasseListview.getItems().get(i).lookup("#kasseAnzahlLabel");
      Label kasseAnzahlName = (Label) kasseListview.getItems().get(i).lookup("#kasseAnzahlName");
      if ((pizza.getName() + " " + whichSize(size)).equals(kasseAnzahlName.getText())) {
        int anzahl = Integer.valueOf(kasseAnzahlLabel.getText()) + 1;
        kasseAnzahlLabel.setText(String.valueOf(anzahl));
        return;
      }
    }
    kasseContr = new RowKasseController();
    loader.setController(kasseContr);

    Pane rootPane = loader.load();
    kasseContr.init(pizza, size);

    this.kasseListview.getItems().add(rootPane);
    //TODO: Label des Gesamten Preises muss sich aktualisieren

  }

  private void eintragHinzufuegen() throws IOException {
    FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewPizzaView.fxml").toURI().toURL());
    Pizza pizza = new Pizza();
    final InsertPizzaViewController insertPizzaViewController = new InsertPizzaViewController(pizza);
    loader.setController(insertPizzaViewController);
    insertPizzaViewController.init(loader, primaryStage);

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

  private String whichSize(int size) {
    switch (size) {
      case 1:
        return "(klein)";
      case 2:
        return "(mittel)";
      case 3:
        return "(groß)";
      case 4:
        return "(Familie)";
    }

    return "";

  }
}
