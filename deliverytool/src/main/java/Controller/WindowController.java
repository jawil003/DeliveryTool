/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import App.JavaFXApplication;
import Model.Kasse.*;
import Model.PizzenDB.Pizza;
import Model.PizzenDB.Pizzavadministration;
import Tools.LinkFetcher;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class WindowController {

  /**
   * @author Jannik Will
   * @version 1.0
   */
    private static final String ROW_FXML = "deliverytool/Fxml/Cells/RowPizzenListcell.fxml";
    private static final String ROW2_FXML = "deliverytool/Fxml/Cells/RowKasseListcell.fxml";
    private InsertPizzaViewController parentController;
    private RowPizzasController pizzenContr;
    private Pizzavadministration verw;
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
    private MenuItem schliessenItem;
    @FXML
    private MenuItem neustartItem;
    @FXML
    private Label gesamterPreisLabel;
    private Stage primaryStage;
    private Registryadministration verwk;

    private Parent pane;
    private Scene scene;

    /**
     */
    public WindowController() {

        this.verw = new Pizzavadministration();
        this.verwk = new Registryadministration();
    }

    // A Pizza is added

    public void loadFXMLItemsAgain() throws IOException {

        final String s = LinkFetcher.normalizePath(Paths.get("deliverytool/Fxml/Window.fxml").toAbsolutePath().toString(), "/deliverytool");
        FXMLLoader loader = new FXMLLoader(new File(s).toURI().toURL());
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


  private void addPizzaListener(Pizza pizza){
      pizzenContr.getKleinButton().setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              try {
                  addKasseneintrag(pizza, 1);
              } catch (IOException | AddingKassenEintragException e) {
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
              } catch (IOException | AddingKassenEintragException e) {
                  e.printStackTrace();
              } catch (InvalidEntryException e) {
                  e.printStackTrace();
              }
          }
      });

      pizzenContr.getGrossButton().setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              try {
                  addKasseneintrag(pizza, 3);
              } catch (IOException | AddingKassenEintragException e) {
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
              } catch (IOException | InvalidEntryException | AddingKassenEintragException e) {
                  e.printStackTrace();
              }
          }
      });

  }

  private void addListener() throws MalformedURLException {

      verw.addListener(new PizzenViewListener());

      // create rows
      for (Pizza pizza : this.verw.getList()) {
          addPizzaRow(pizza);
          addListenerPizzaRow(pizza);
      }
      // Actions:

      ServiceAnsicht.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              WindowServiceController controller = new WindowServiceController();
              try {
                  controller.loadFXMLItemsAgain();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              try {
                  controller.init(primaryStage, verwk);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      });

      kasseAnsicht.setOnAction(new KasseAnsichtItemListener());

      schliessenItem.setOnAction(new EventHandler<ActionEvent>() {
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
                              final JavaFXApplication javaFXApplication = new JavaFXApplication();
                                      javaFXApplication.init();
                                      javaFXApplication.start(new Stage());
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                      });
                  }
              });
          }
      });

      eintragHinzufuegenItem.setOnAction(new EintragHinzufuegenListener());



      bonDruckenItem.setOnAction(new BonDruckenListener());

      allesLoeschenItem.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              kasseListview.getItems().clear();
              verwk.getKassenEintraege().clear();
              gesamterPreisLabel.setText(verwk.toEuroValue());
          }
      });



      ausgewaehltLoeschen.setOnAction(
              new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent event) {
                      try {
                          deleteSelected();
                      } catch (NoSuchEntryException e) {
                          e.printStackTrace();
                      }
                  }
              });

      kasseListview.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
              try {
                  deleteSelected(event);
              } catch (NoSuchEntryException e) {
                  e.printStackTrace();
              }
          }
      });
  }

  /**
   * Initalize the MainWindow
   *
   * */
  public void init(Stage primaryStage) throws MalformedURLException {
      this.primaryStage=primaryStage;
      addListener();
    }


  /**
   * Add a new Row for a choosable Pizza
   * @param pizza
   */
  private void addPizzaRow(Pizza pizza) throws MalformedURLException {
    // load fxml
    try {
      FXMLLoader loader = new FXMLLoader(new File(ROW_FXML).toURI().toURL());

            // set controller
            pizzenContr = new RowPizzasController();
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

    private void addListenerPizzaRow(Pizza pizza) {
        pizzenContr
                .getKleinButton()
                .setOnAction(
                        event -> {
                            try {
                                addKasseneintrag(pizza, 1);
                                gesamterPreisLabel.setText(verwk.toEuroValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

        pizzenContr
                .getMittelButton()
                .setOnAction(
                        event -> {
                            try {
                                addKasseneintrag(pizza, 2);
                                gesamterPreisLabel.setText(verwk.toEuroValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });


        pizzenContr
                .getGrossButton()
                .setOnAction(
                        event -> {
                            try {
                                addKasseneintrag(pizza, 3);
                                gesamterPreisLabel.setText(verwk.toEuroValue());
                            } catch (AddingKassenEintragException | IOException | InvalidEntryException e) {
                                e.printStackTrace();
                            }
                        });

        pizzenContr
                .getFamilieButton()
                .setOnAction(
                        event -> {
                            try {
                                addKasseneintrag(pizza, 4);
                                gesamterPreisLabel.setText(verwk.toEuroValue());
                            } catch (AddingKassenEintragException | InvalidEntryException | IOException e) {
                                e.printStackTrace();
                            }
                        });
    }

    private void addKasseneintrag(OrderedPizza pizza) throws IOException {

      if(!verwk.contains(pizza)) {


          FXMLLoader loader = new FXMLLoader(new File(ROW2_FXML).toURI().toURL());

          RowRegisterController kasseContr = new RowRegisterController();
          loader.setController(kasseContr);

          Pane rootPane = loader.load();
          kasseContr.init(pizza);

          this.kasseListview.getItems().add(rootPane);
      }else{
         for(Pane p:kasseListview.getItems()){
             final Label lookup = (Label) ((AnchorPane) p).lookup("#kasseAnzahlLabel");
             final int text = Integer.valueOf(lookup.getText());
             lookup.setText(String.valueOf(text+1));
         }
      }

        verwk.addKassenEintrag(pizza);
    }

    /**
     * Add a new Row for a choosed Pizza
     *
     * @param pizza Pizza Entry where the price is extracted from
     * @param size the Size of the Pizza (1-4 as little - family)
     * @throws AddingKassenEintragException When adding the entry gone wrong
     */
    private void addKasseneintrag(Pizza pizza, int size) throws AddingKassenEintragException, InvalidEntryException, IOException {
        OrderedPizza bp = new OrderedPizza(pizza.getName());
        //DecimalFormat df2 = new DecimalFormat("#,##");

            switch (size) {
                case 1:
                    bp.setGroeße('k');
                    bp.setPreis(pizza.getPreisKlein().orElse(0.0));
                    //verwk.addKassenEintrag(new OrderedPizza(pizza.getName(), pizza.getPreisKlein().orElse(0.0), 'k'));
                    break;
                case 2:
                    bp.setGroeße('m');
                    bp.setPreis(pizza.getPreisMittel().orElse(0.0));
                    //verwk.addKassenEintrag(new OrderedPizza(pizza.getName(), pizza.getPreisMittel().orElse(0.0), 'm'));
                    break;
                case 3:
                    bp.setGroeße('g');
                    bp.setPreis(pizza.getPreisGroß().orElse(0.0));
                    //verwk.addKassenEintrag(new OrderedPizza(pizza.getName(), pizza.getPreisGroß().orElse(0.0), 'g'));
                    break;
                case 4:
                    bp.setGroeße('f');
                    bp.setPreis(pizza.getPreisFamilie().orElse(0.0));
                    //verwk.addKassenEintrag(new OrderedPizza(pizza.getName(), pizza.getPreisFamilie().orElse(0.0), 'f'));
                    break;
            }


            addKasseneintrag(bp);

        }


        /** @throws IOException */
        private void eintragHinzufuegen () throws IOException {
            FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewPizzaView.fxml").toURI().toURL());
            Pizza pizza = new Pizza();
            final InsertPizzaViewController insertPizzaViewController = new InsertPizzaViewController(pizza);
            loader.setController(insertPizzaViewController);
            this.parentController = insertPizzaViewController;
            insertPizzaViewController.init(primaryStage);

            insertPizzaViewController.getOkButton().setOnAction(new OkButtonListener());

        }

        /**
         * Get the name matching the number in the int size param
         *
         * @param size the Size of a Pizza (1-4 means Little-FamilyPizza)
         * @return the String which needs to be inserted in the addkasseneintrag method for filling the Label in the Row of KassenListView
         */
        @org.jetbrains.annotations.NotNull
        private String whichSize ( int size){
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
         */
        private void deleteSelected (KeyEvent event) throws NoSuchEntryException {
            if (kasseListview.isFocused()) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    deleteSelected();
                }
            }
        }

        /**
        *@todo Double same Entry gesamterPreis doesn't recalculate
        *@body Possible reason is in Registryadministration Set with Wrapper object which isn't used yet
        */

        /**
         * Delete the selected Entry (like above)
         * */
        public void deleteSelected () throws NoSuchEntryException {
            final int selectedIndex = kasseListview.getSelectionModel().getSelectedIndex();
            final RegistryEntryWrapper kassenEintrag = verwk.get(selectedIndex);
            final int size = kassenEintrag.getSize();
            if (size>1) {
                final Label lookup = (Label) kasseListview.getItems().get(selectedIndex).lookup("#kasseAnzahlLabel");
                kassenEintrag.setSize(size-1);
                lookup.setText(String.valueOf(Integer.valueOf(lookup.getText()) - 1));
            } else if(size==1){
                verwk.remove(kassenEintrag);
                kasseListview.getItems().remove(selectedIndex);


            }
            gesamterPreisLabel.setText(verwk.toEuroValue());
        }

        /**
         * @return the ListView of choosable Pizza Entries
         */
        public ListView<Pane> getPizzenListview () {
            return pizzenListview;
        }

        /**
         * @param pizzenListview
         */
        public void setPizzenListview (ListView < Pane > pizzenListview) {
            this.pizzenListview = pizzenListview;
        }

        /**
         * @return the ListView of choosed Pizza Entries
         */
        public ListView<Pane> getKasseListview () {
            return kasseListview;
        }

        /**
         * @param kasseListview
         */
        public void setKasseListview (ListView < Pane > kasseListview) {
            this.kasseListview = kasseListview;
        }

        /**
         * @return the menuBar of the main View
         */
        public MenuBar getMenuBar () {
            return menuBar;
        }

        /**
         * @param menuBar
         */
        public void setMenuBar (MenuBar menuBar){
            this.menuBar = menuBar;
        }

    public void show() {
            primaryStage.show();
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
         * The Listener is triggered when a new Item is added to the KasseView so that the gesamterPreis
         * Label is increased automatically
         */


        private class KasseViewListener implements ListChangeListener<OrderedPizza> {

            @Override
            public void onChanged(Change<? extends OrderedPizza> c) {
                gesamterPreisLabel.setText(verwk.toEuroValue());
            }
        }

        private class PizzenViewListener implements ListChangeListener<Pizza> {

            @Override
            public void onChanged(Change<? extends Pizza> c) {
                while (c.next()) {
                    final List<? extends Pizza> addedSubList = c.getAddedSubList();
                    for (Pizza p : addedSubList) {
                        try {
                            addPizzaRow(p);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        addListenerPizzaRow(p);
                    }
                }
            }
        }


        private class BonDruckenListener implements EventHandler<ActionEvent> {

            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Rechnung speichern unter");
                chooser.setInitialFileName("Rechnung.pdf");
                try {
                    final double gesamterPreis = verwk.getGesamterPreis();
                    BonCreator creator = new BonCreator(verwk, gesamterPreis , (chooser.showSaveDialog(primaryStage.getScene().getWindow())).getAbsolutePath());
                    creator.addPizzas(verwk.getKassenEintraege(), gesamterPreis);
                    creator.close();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        class AddingKassenEintragException extends Exception {
            AddingKassenEintragException(String message) {
                super(message);
            }
        }

        class InvalidInstanciationInsertPizzaViewController extends Exception {
            InvalidInstanciationInsertPizzaViewController(String message) {
                super(message);
            }
        }

        public class KasseAnsichtItemListener implements EventHandler<ActionEvent> {

            /**
             * Invoked when a specific event of the type for which this handler is
             * registered happens.
             *
             * @param event the event which occurred
             */
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadFXMLItemsAgain();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    addListener();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //gesamterPreisRecalculate();
                gesamterPreisLabel.setText(verwk.toEuroValue());

                try {
                    loadKassenEintraege();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                primaryStage.getScene().setRoot(pane);
                primaryStage.show();
            }
        }

    private void loadKassenEintraege() throws IOException {
            for(RegistryEntryWrapper e: verwk.getKassenEintraege()){
                addKasseneintrag(e.getPizza());
                addListener();
            }
    }

    private class EintragHinzufuegenListener implements EventHandler<ActionEvent> {
        /**
         * Invoked when a specific event of the type for which this handler is
         * registered happens.
         *
         * @param event the event which occurred
         */
        @Override
        public void handle(ActionEvent event) {

        }
    }

    public InsertPizzaViewController getParentController() {
        return parentController;
    }

    public void setParentController(InsertPizzaViewController parentController) {
        this.parentController = parentController;
    }

    public RowPizzasController getPizzenContr() {
        return pizzenContr;
    }

    public void setPizzenContr(RowPizzasController pizzenContr) {
        this.pizzenContr = pizzenContr;
    }

    public Pizzavadministration getVerw() {
        return verw;
    }

    public void setVerw(Pizzavadministration verw) {
        this.verw = verw;
    }

    public GridPane getGridpane() {
        return gridpane;
    }

    public void setGridpane(GridPane gridpane) {
        this.gridpane = gridpane;
    }

    public MenuItem getAusgewaehltLoeschen() {
        return ausgewaehltLoeschen;
    }

    public void setAusgewaehltLoeschen(MenuItem ausgewaehltLoeschen) {
        this.ausgewaehltLoeschen = ausgewaehltLoeschen;
    }

    public MenuItem getBonDruckenItem() {
        return bonDruckenItem;
    }

    public void setBonDruckenItem(MenuItem bonDruckenItem) {
        this.bonDruckenItem = bonDruckenItem;
    }

    public MenuItem getAllesLoeschenItem() {
        return allesLoeschenItem;
    }

    public void setAllesLoeschenItem(MenuItem allesLoeschenItem) {
        this.allesLoeschenItem = allesLoeschenItem;
    }

    public MenuItem getEintragHinzufuegenItem() {
        return eintragHinzufuegenItem;
    }

    public void setEintragHinzufuegenItem(MenuItem eintragHinzufuegenItem) {
        this.eintragHinzufuegenItem = eintragHinzufuegenItem;
    }

    public MenuItem getUeberItem() {
        return ueberItem;
    }

    public void setUeberItem(MenuItem ueberItem) {
        this.ueberItem = ueberItem;
    }

    public MenuItem getKasseAnsicht() {
        return kasseAnsicht;
    }

    public void setKasseAnsicht(MenuItem kasseAnsicht) {
        this.kasseAnsicht = kasseAnsicht;
    }

    public MenuItem getZubereitungAnsicht() {
        return zubereitungAnsicht;
    }

    public void setZubereitungAnsicht(MenuItem zubereitungAnsicht) {
        this.zubereitungAnsicht = zubereitungAnsicht;
    }

    public MenuItem getServiceAnsicht() {
        return ServiceAnsicht;
    }

    public void setServiceAnsicht(MenuItem serviceAnsicht) {
        ServiceAnsicht = serviceAnsicht;
    }

    public MenuItem getSchliessenItem() {
        return schliessenItem;
    }

    public void setSchliessenItem(MenuItem schliessenItem) {
        this.schliessenItem = schliessenItem;
    }

    public MenuItem getNeustartItem() {
        return neustartItem;
    }

    public void setNeustartItem(MenuItem neustartItem) {
        this.neustartItem = neustartItem;
    }

    public Label getGesamterPreisLabel() {
        return gesamterPreisLabel;
    }

    public void setGesamterPreisLabel(Label gesamterPreisLabel) {
        this.gesamterPreisLabel = gesamterPreisLabel;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Registryadministration getVerwk() {
        return verwk;
    }

    public void setVerwk(Registryadministration verwk) {
        this.verwk = verwk;
    }

    public Parent getPane() {
        return pane;
    }

    public void setPane(Parent pane) {
        this.pane = pane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
