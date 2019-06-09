/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import App.JavaFXApplication;
import Model.Kasse.*;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class WindowController {

  /**
   * @author Jannik Will
   * @version 1.0
   */

    private static final String ROW2_FXML = "deliverytool/Fxml/Cells/RowKasseListcell.fxml";
    private InsertPizzaViewController parentController;
    private RowPizzenController pizzenContr;
    private RowKasseController kasseContr;
    private double gesamterPreis = 0.00;
    private Pizzaverwaltung verw;
    @FXML
    private ListView<Pane> pizzenListview;
    @FXML
    private GridPane gridpane;
    @FXML
    private ListView<Pane> kasseListview;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem ausgewaehltLoeschen;
    @FXML
    private MenuItem bonDruckenItem;
    @FXML
    private MenuItem allesLoeschenItem;
    @FXML
    private MenuItem eintragHinzufuegenItem;
    @FXML
    private MenuItem ueberItem;
    @FXML
    private MenuItem kasseAnsicht;
    @FXML
    private MenuItem zubereitungAnsicht;
    @FXML
    private MenuItem ServiceAnsicht;
    @FXML
    private MenuItem schließenItem;
    @FXML
    private MenuItem neustartItem;
    @FXML
    private Label gesamterPreisLabel;
    private Stage primaryStage;
    private Kassenverwaltung verwk;
    private int size;

    private Parent pane;
    private Scene scene;

    /**
     */
    public WindowController() {

        this.verw = new Pizzaverwaltung();
        this.verwk = new Kassenverwaltung();
    }

    /**
     * @return String for the source of the Kasse Row
     */
    public static String getRow2Fxml() {
        return ROW2_FXML;
    }

    /**
     * @return String for the source of the Pizza Row
     */
    public static String getRowFxml() {
        return ROW_FXML;
    }

    // A Pizza is added

    public void loadFXMLItemsAgain() throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/Window.fxml").toURI().toURL());
        if (loader.getController() == null) {
            loader.setController(this);
        }
        pane = loader.load();
    }

    /** @return String for the source of the Pizza Row */
    public static String getRowFxml() {
        return ROW_FXML;
    }

    // A Pizza is added

  /** @return String for the source of the Kasse Row*/
  public static String getRow2Fxml() {
    return ROW2_FXML;
  }

  /**
   * Initalize the MainWindow
   * @param primaryStage
   * @param scene
   * @param rootPane
   */
  public void init(Stage primaryStage, Scene scene, Parent rootPane) {

        verwk.getKassenEintraege().addListener(new KasseViewListener());
        //change();

        // create rows
        for (Pizza pizza : this.verw.getList()) {
            addPizzaRow(pizza, ROW_FXML);

            pizzenContr
                    .getKleinButton()
                    .setOnAction(
                            event -> {
                                try {
                                    addKasseneintrag(pizza, 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (InvalidEntryException e) {
                                    e.printStackTrace();
                                }
                            });

            bonDruckenItem.setOnAction(new BonDruckenListener());

            pizzenContr
                    .getMittelButton()
                    .setOnAction(
                            new EventHandler<ActionEvent>() {
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

            allesLoeschenItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            kasseListview.getItems().clear();
                            verwk.getKassenEintraege().clear();
                            gesamterPreis = 0.0;
                            gesamterPreisLabel.setText(String.format("%.2f", gesamterPreis) + "€");
                        }
                    });

            pizzenContr
                    .getGrossButton()
                    .setOnAction(
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        addKasseneintrag(pizza, 3);
                                    } catch (IOException | InvalidEntryException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

            pizzenContr
                    .getFamilieButton()
                    .setOnAction(
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        addKasseneintrag(pizza, 4);
                                    } catch (IOException | InvalidEntryException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

        }

      //Actions:

      schließenItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          Platform.exit();
        }
      });

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

            bonDruckenItem.setOnAction(new BonDruckenListener());

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

  /**
   * Add a new Row for a choosable Pizza
   * @param pizza
   * @param fxmlPath
   */
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

  /**
   * Add a new Row for a choosed Pizza
   * @param pizza
   * @param size
   * @throws IOException
   * @throws InvalidEntryException
   */
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

  /** @throws IOException */
  private void eintragHinzufuegen() throws IOException {
    FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewPizzaView.fxml").toURI().toURL());
    Pizza pizza = new Pizza();
    final InsertPizzaViewController insertPizzaViewController = new InsertPizzaViewController(pizza);
    loader.setController(insertPizzaViewController);
    this.parentController = insertPizzaViewController;
    insertPizzaViewController.init(loader, primaryStage);

    insertPizzaViewController.getOkButton().setOnAction(new OkButtonListener());

  }

    /**
     * Get the name matching the number in the int siza param
     *
     * @param size
     * @return
     */
    @org.jetbrains.annotations.NotNull
    private String whichSize(int size) {
        this.size = size;
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

    // Getters and setters:

  /**
   * Delete the selected row Entry in the KasseView
   *  @param event */
  private void deleteSelected(KeyEvent event) {
    if (kasseListview.isFocused()) {
      if (event.getCode() == KeyCode.BACK_SPACE) {
        deleteSelected();
      }
    }
  }

  /**
   * Delete the selected Entry (like above)
   * */
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

  /**
   * @return the ListView of choosable Pizza Entries
   */
  public ListView<Pane> getPizzenListview() {
    return pizzenListview;
  }

  /**
   * @param pizzenListview
   */
  public void setPizzenListview(ListView<Pane> pizzenListview) {
    this.pizzenListview = pizzenListview;
  }

  /**
   * @return the ListView of choosed Pizza Entries
   */
  public ListView<Pane> getKasseListview() {
    return kasseListview;
  }

  /**
   * @param kasseListview
   */
  public void setKasseListview(ListView<Pane> kasseListview) {
    this.kasseListview = kasseListview;
  }

  /**
   * @return the menuBar of the main View
   */
  public MenuBar getMenuBar() {
    return menuBar;
  }

  /**
   * @param menuBar
   */
  public void setMenuBar(MenuBar menuBar) {
    this.menuBar = menuBar;
  }

    // Listener:

  /**
   * The Listener which is triggered when the OkButon is pressed */
  private class OkButtonListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      try {
        verw.add(new Pizza(parentController.getNameField().getText(), null,
                Double.valueOf(parentController.getPreisKleinField().getText()),
                Double.valueOf(parentController.getPreisMittelField().getText()),
                Double.valueOf(parentController.getPreisGrossField().getText()),
                Double.valueOf(parentController.getPreisFamilieField().getText())));
        parentController.close();
      } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Preis(e) ungültig");
        alert.setHeaderText("Bitte gültige Preis(e) eingeben und alle Felder ausfüllen");
        alert.showAndWait();
      }
    }
    }

  /**
   * The Listener which is triggered when the menuItem "EintragHinzufuegen" is pressed
   * */
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

  /**
   * The Listener is triggered when a new Item is added to the KasseView so that the gesamterPreis Label is increased automatically
   * */
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


    private class BonDruckenListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
      try {
        BonCreator creator = new BonCreator(verwk, gesamterPreis);
        creator.addPizzas(verwk.getKassenEintraege());
          FileChooser c = new FileChooser();
          c.setInitialFileName("Rechnung.pdf");
          creator.close(c.showSaveDialog(primaryStage).getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
