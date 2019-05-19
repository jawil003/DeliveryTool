package Controller;

import App.JavaFXApplication;
import Model.Kasse.BestelltePizza;
import Model.Kasse.InvalidEntryException;
import Model.Kasse.KassenEintrag;
import Model.Kasse.Kassenverwaltung;
import Model.PizzenDB.Pizza;
import Model.PizzenDB.Pizzaverwaltung;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class WindowController {

  protected static final String ROW_FXML = "deliverytool/Fxml/Cells/RowPizzenListcell.fxml";
  protected static final String ROW2_FXML = "deliverytool/Fxml/Cells/RowKasseListcell.fxml";
  InsertPizzaViewController parentController;
  private Pizzaverwaltung verw;
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
  private Kassenverwaltung verwk;

  public WindowController(Pizzaverwaltung verw, Kassenverwaltung verwk, Stage primaryStage) {

    this.verw = verw;
    this.verwk = verwk;
    this.primaryStage = primaryStage;
  }

  public static String getRowFxml() {
    return ROW_FXML;
  }

  public static String getRow2Fxml() {
    return ROW2_FXML;
  }

  public void init(Stage primaryStage, Scene scene, Parent rootPane) {

    verwk.getKassenEintraege().addListener(new KasseViewListener());

    // create rows
    for (Pizza pizza : this.verw.getPizzen()) {
      addPizzaRow(pizza, ROW_FXML);

      //Actions:

        schließenItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

      //FIXME: NeustartItem causes a loop and freeze of gui (DB Access is not yet a different thread

        neustartItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                      primaryStage.close();
                      Platform.runLater(() -> {
                        try {
                          new JavaFXApplication().start(new Stage());
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      });
                    }
                });
            }
        });

      eintragHinzufuegenItem.setOnAction(new EintragHinzufuegenListener());

      pizzenContr.getKleinButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 1);
          } catch (IOException e) {
            e.printStackTrace();
          } catch (InvalidEntryException e) {
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
          } catch (InvalidEntryException e) {
            e.printStackTrace();
          }
        }
      });

      allesLoeschenItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          kasseListview.getItems().clear();
          verwk.getKassenEintraege().clear();
          gesamterPreis = 0.0;
          gesamterPreisLabel.setText(String.format("%.2f", gesamterPreis) + "€");
        }
      });

        pizzenContr.getGrossButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 3);
          } catch (IOException e) {
            e.printStackTrace();
          } catch (InvalidEntryException e) {
            e.printStackTrace();
          }
        }
      });

        pizzenContr.getFamilieButton().setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            addKasseneintrag(pizza, 4);
          } catch (IOException | InvalidEntryException e) {
            e.printStackTrace();
          }
        }
      });

      ausgewähltLoeschen.setOnAction(
              new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  deleteSelected();
                }
              });

      kasseListview.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
          deleteSelected(event);
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

  private void addKasseneintrag(Pizza pizza, int size) throws IOException, InvalidEntryException {
    BestelltePizza bp = new BestelltePizza(pizza.getName());
    DecimalFormat df2 = new DecimalFormat("#,##");

    switch (size) {
      case 1:
        bp.setGroeße('k');
        bp.setPreis(pizza.getPreisKlein().orElse(0.0));
        break;
      case 2:
        bp.setGroeße('m');
        bp.setPreis(pizza.getPreisMittel().orElse(0.0));
        break;
      case 3:
        bp.setGroeße('g');
        bp.setPreis(pizza.getPreisGroß().orElse(0.0));
        break;
      case 4:
        bp.setGroeße('f');
        bp.setPreis(pizza.getPreisFamilie().orElse(0.0));
        break;
    }

    verwk.addKassenEintrag(bp);

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

  }

  private void eintragHinzufuegen() throws IOException {
    FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewPizzaView.fxml").toURI().toURL());
    Pizza pizza = new Pizza();
    final InsertPizzaViewController insertPizzaViewController = new InsertPizzaViewController(pizza);
    loader.setController(insertPizzaViewController);
    this.parentController = insertPizzaViewController;
    insertPizzaViewController.init(loader, primaryStage);

    insertPizzaViewController.getOkButton().setOnAction(new OkButtonListener());

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

  private void deleteSelected(KeyEvent event) {
    if (kasseListview.isFocused()) {
      if (event.getCode() == KeyCode.BACK_SPACE) {
        deleteSelected();
      }
    }
  }

  public void deleteSelected() {
    final int selectedIndex = kasseListview.getSelectionModel().getSelectedIndex();
    final KassenEintrag kassenEintrag = verwk.removeKassenEintrag(selectedIndex);
    final Label lookup = (Label) kasseListview.getItems().get(selectedIndex).lookup("#kasseAnzahlLabel");
    if (Integer.valueOf(lookup.getText()) > 1) {
      lookup.setText(String.valueOf(Integer.valueOf(lookup.getText()) - 1));
    } else {
      kasseListview.getItems().remove(selectedIndex);
    }
    gesamterPreis -= kassenEintrag.getPreis();
    gesamterPreisLabel.setText(String.format("%.2f", gesamterPreis) + "€");
  }

  //Listener:

  private class OkButtonListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      try {
        verw.add(new Pizza(parentController.getNameField().getText(), null,
                Double.valueOf(parentController.getPreisKleinFiled().getText()),
                Double.valueOf(parentController.getPreisMittelField().getText()),
                Double.valueOf(parentController.getPreisGroßField().getText()),
                Double.valueOf(parentController.getPreisFamilieField().getText())));
        parentController.close();
      } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Preis(e) ungültig");
        alert.setHeaderText("Bitte gültige Preis(e) eingeben und alle Felder ausfüllen");
        alert.showAndWait();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  private class EintragHinzufuegenListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      try {
        eintragHinzufuegen();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private class KasseViewListener implements ListChangeListener<BestelltePizza> {

    @Override
    public void onChanged(Change<? extends BestelltePizza> c) {
      while (c.next()) {
        final List<? extends BestelltePizza> addedSubList = c.getAddedSubList();
        for (BestelltePizza p : addedSubList) {
          gesamterPreis += p.getPreis();

        }
        gesamterPreisLabel.setText(String.format("%.2f", gesamterPreis) + "€");

      }
    }
  }

}
