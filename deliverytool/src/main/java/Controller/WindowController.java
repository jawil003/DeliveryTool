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
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Jannik Will
 * @version 1.0
 */
public class WindowController {
    //FXML-Integration variables:
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
    private MenuItem serviceAnsicht;
    @FXML
    private MenuItem schliessenItem;
    @FXML
    private MenuItem neustartItem;
    @FXML

    //Other variables:
    private Label gesamterPreisLabel;
    private Stage primaryStage;
    private static final String FXML_CELLS_ROW_PIZZEN_LISTCELL_FXML = "deliverytool/Fxml/Cells/RowPizzenListcell.fxml";
    private static final String FXML_CELLS_ROW_KASSE_LISTCELL_FXML = "deliverytool/Fxml/Cells/RowKasseListcell.fxml";
    private static final String FXML_WINDOW_FXML = "deliverytool/Fxml/Window.fxml";
    private InsertPizzaViewController insertPizzaViewController;
    private RowPizzasController pizzasController;
    private Pizzavadministration pizzavadministration;
    private Registryadministration registryadministration;
    private Parent pane;
    private Scene scene;

    /**
     */
    public WindowController() {

        this.pizzavadministration = new Pizzavadministration();
        this.registryadministration = new Registryadministration();
    }

    // A Pizza is added

    public void loadFXMLItemsAgain() throws IOException {

        final String s = LinkFetcher.normalizePath(Paths.get(FXML_WINDOW_FXML).toAbsolutePath().toString(), "/deliverytool");
        FXMLLoader loader = new FXMLLoader(new File(s).toURI().toURL());
        if (loader.getController() == null) {
            loader.setController(this);
        }
        pane = loader.load();
    }

    public static String getFxmlCellsRowPizzenListcellFxml() {
        return FXML_CELLS_ROW_PIZZEN_LISTCELL_FXML;
    }

    /**
     * @throws MalformedURLException
     */
  private void loadPizzaEntries() throws MalformedURLException {
      // create rows
      for (Pizza pizza : this.pizzavadministration.getList()) {
          addPizzaRow(pizza);
          addListenerPizzaRow(pizza);
      }
  }

    /**
     * @throws MalformedURLException
     */
  private void addListener() throws MalformedURLException {

      pizzavadministration.addListener(new PizzenViewListener());

      registryadministration.addListener(new RegistryAdministrationListener());

      // Actions:

      serviceAnsicht.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              WindowServiceController controller = new WindowServiceController();
              try {
                  controller.loadFXMLItemsAgain();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              try {
                  controller.init(primaryStage, registryadministration);
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

      allesLoeschenItem.setOnAction(event -> {
          kasseListview.getItems().clear();
          registryadministration.getKassenEintraege().clear();
          gesamterPreisLabel.setText(registryadministration.toEuroValue());
      });



      ausgewaehltLoeschen.setOnAction(
              event -> {
                  try {
                      ListViewMode mode = null;
                      if(kasseListview.isFocused()) {
                          mode = ListViewMode.KassenListView;
                      }else if (pizzenListview.isFocused()){
                          mode = ListViewMode.PizzenListView;
                      }
                      deleteSelected(mode);
                  } catch (NoSuchEntryException e) {
                      e.printStackTrace();
                  }
              });

      kasseListview.setOnKeyPressed(event -> {
          try {
              deleteSelected(event);
          } catch (NoSuchEntryException e) {
              e.printStackTrace();
          }
      });
  }

  /**
   * Initalize the MainWindow
   * @param primaryStage the Stage in which the Window(-pane) should be inizialized
   * @param loader*/
  public void init(Stage primaryStage, FXMLLoader loader) throws MalformedURLException {
      if (loader.getController() == null)
          loader.setController(this);
      this.primaryStage=primaryStage;
      addListener();
      loadPizzaEntries();
    }


  /**
   * @param pizza The Entry which a new Row should be generated in Pizzalistview
   */
  private void addPizzaRow(Pizza pizza) throws MalformedURLException {
    // load fxml
    try {
      FXMLLoader loader = new FXMLLoader(new File(FXML_CELLS_ROW_PIZZEN_LISTCELL_FXML).toURI().toURL());

            // set controller
            pizzasController = new RowPizzasController();
            loader.setController(pizzasController);


            Pane rootPane = loader.load();

            // initialize tab controller
            pizzasController.init(pizza);

            this.pizzenListview.getItems().add(rootPane);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String getFxmlCellsRowKasseListcellFxml() {
        return FXML_CELLS_ROW_KASSE_LISTCELL_FXML;
    }

    public static String getFxmlWindowFxml() {
        return FXML_WINDOW_FXML;
    }


        /** @throws IOException */
        private void eintragHinzufuegen() throws IOException {
            //FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewPizzaView.fxml").toURI().toURL());
            Pizza pizza = new Pizza();
            final InsertPizzaViewController insertPizzaViewController = new InsertPizzaViewController(pizza);
            //loader.setController(insertPizzaViewController);
            this.insertPizzaViewController = insertPizzaViewController;
            insertPizzaViewController.loadFXMLItemsAgain();
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
        private String whichSize (PizzaSize size){
            switch (size) {
                case Small:
                    return "(klein)";
                case Middle:
                    return "(mittel)";
                case Big:
                    return "(groß)";
                case Family:
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
                    deleteSelected(ListViewMode.KassenListView);
                }
            }
        }

        /**
         * Delete the selected Entry (like above)
         * */
        public void deleteSelected (ListViewMode mode) throws NoSuchEntryException {

            switch (mode){
                case KassenListView:
                    final int selectedIndex = kasseListview.getSelectionModel().getSelectedIndex();
                    final RegisterEntry registerEntry = registryadministration.get(selectedIndex);
                    final int size = registryadministration.getSize(registerEntry);
                    if (size>1) {
                        final Label lookup = (Label) kasseListview.getItems().get(selectedIndex).lookup("#kasseAnzahlLabel");
                        lookup.setText(String.valueOf(Integer.valueOf(lookup.getText()) - 1));
                    } else if(size==1){
                        kasseListview.getItems().remove(selectedIndex);
                    }

                    registryadministration.remove(registerEntry);
                    gesamterPreisLabel.setText(registryadministration.toEuroValue());
                case PizzenListView:
            }
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
                    pizzavadministration.add(new Pizza(insertPizzaViewController.getNameField().getText(), null,
                            Double.valueOf(insertPizzaViewController.getPreisKleinField().getText()),
                            Double.valueOf(insertPizzaViewController.getPreisMittelField().getText()),
                            Double.valueOf(insertPizzaViewController.getPreisGrossField().getText()),
                            Double.valueOf(insertPizzaViewController.getPreisFamilieField().getText())));
                    insertPizzaViewController.close();
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
                gesamterPreisLabel.setText(registryadministration.toEuroValue());
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
                    final double gesamterPreis = registryadministration.getGesamterPreis();
                    BonCreator creator = new BonCreator(registryadministration, gesamterPreis , (chooser.showSaveDialog(primaryStage.getScene().getWindow())).getAbsolutePath());
                    creator.addPizzas(registryadministration.getKassenEintraege(), gesamterPreis);
                    creator.close();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        class AddingRegisterEntryException extends Exception {
            AddingRegisterEntryException(String message) {
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
                gesamterPreisLabel.setText(registryadministration.toEuroValue());

                try {
                    loadKassenEintraege();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchEntryException e) {
                    e.printStackTrace();
                }

                final ObservableList<Node> children = ((VBox) scene.getRoot()).getChildren();
                children.remove(2);
                children.add(pane);


                primaryStage.show();
            }
        }

    /**
     * @param pizza The pizza Entry which should be connected with Listeners of Buttons K,M,B,F
     */
    private void addListenerPizzaRow(Pizza pizza) {
        pizzasController
                .getKleinButton()
                .setOnAction(
                        event -> {
                            try {
                                addRegisterEntry(pizza, PizzaSize.Small);
                                gesamterPreisLabel.setText(registryadministration.toEuroValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

        pizzasController
                .getMittelButton()
                .setOnAction(
                        event -> {
                            try {
                                addRegisterEntry(pizza, PizzaSize.Middle);
                                gesamterPreisLabel.setText(registryadministration.toEuroValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });


        pizzasController
                .getGrossButton()
                .setOnAction(
                        event -> {
                            try {
                                addRegisterEntry(pizza, PizzaSize.Big);
                                gesamterPreisLabel.setText(registryadministration.toEuroValue());
                            } catch (AddingRegisterEntryException | IOException | InvalidEntryException | NoSuchEntryException e) {
                                e.printStackTrace();
                            }
                        });

        pizzasController
                .getFamilieButton()
                .setOnAction(
                        event -> {
                            try {
                                addRegisterEntry(pizza, PizzaSize.Family);
                                gesamterPreisLabel.setText(registryadministration.toEuroValue());
                            } catch (AddingRegisterEntryException | InvalidEntryException | IOException | NoSuchEntryException e) {
                                e.printStackTrace();
                            }
                        });
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
            try {
                eintragHinzufuegen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public InsertPizzaViewController getInsertPizzaViewController() {
        return insertPizzaViewController;
    }

    public void setInsertPizzaViewController(InsertPizzaViewController insertPizzaViewController) {
        this.insertPizzaViewController = insertPizzaViewController;
    }

    public RowPizzasController getPizzasController() {
        return pizzasController;
    }

    public void setPizzasController(RowPizzasController pizzasController) {
        this.pizzasController = pizzasController;
    }

    public Pizzavadministration getPizzavadministration() {
        return pizzavadministration;
    }

    public void setPizzavadministration(Pizzavadministration pizzavadministration) {
        this.pizzavadministration = pizzavadministration;
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
        return serviceAnsicht;
    }

    public void setServiceAnsicht(MenuItem serviceAnsicht) {
        this.serviceAnsicht = serviceAnsicht;
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

    public Registryadministration getRegistryadministration() {
        return registryadministration;
    }

    public void setRegistryadministration(Registryadministration registryadministration) {
        this.registryadministration = registryadministration;
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

    private class RegistryAdministrationListener implements MapChangeListener<RegisterEntry, Integer> {
        /**
         * Called after a change has been made to an ObservableMap.
         * This method is called on every elementary change (put/remove) once.
         * This means, complex changes like keySet().removeAll(Collection) or clear()
         * may result in more than one call of onChanged method.
         *
         * @param change the change that was made
         */
        @Override
        public void onChanged(Change<? extends RegisterEntry, ? extends Integer> change) {
            gesamterPreisLabel.setText(registryadministration.toEuroValue());
        }
    }

    /**
     * @param pizza The Entry for which a new RegisterRow is created
     * @throws IOException
     * @throws NoSuchEntryException
     */
    private void addRegisterEntry(OrderedPizza pizza) throws IOException, NoSuchEntryException {

        if (!registryadministration.contains(pizza)) {


            FXMLLoader loader = new FXMLLoader(new File(FXML_CELLS_ROW_KASSE_LISTCELL_FXML).toURI().toURL());

            RowRegisterController kasseContr = new RowRegisterController();
            loader.setController(kasseContr);

            Pane rootPane = loader.load();
            kasseContr.init(pizza);

            this.kasseListview.getItems().add(rootPane);
            registryadministration.addRegisterEntry(pizza);
        } else {
            String groese = null;
            switch (pizza.getGroeße()) {
                case Small:
                    groese = "(Klein)";
                    break;
                case Middle:
                    groese = "(Mittel)";
                    break;
                case Big:
                    groese = "(Groß)";
                    break;
                case Family:
                    groese = "(Familie)";
                    break;
            }
            for (Pane p : kasseListview.getItems()) {
                if (((Label) p.lookup("#kasseAnzahlName")).getText().contains(pizza.getName() + " " + groese)) {
                    final Label lookup = (Label) p.lookup("#kasseAnzahlLabel");
                    final int text = Integer.valueOf(lookup.getText());
                    lookup.setText(String.valueOf(text + 1));
                    registryadministration.addRegisterEntry(pizza);
                    return;
                }
            }
        }
    }

    /**
     * Add a new Row for a choosed Pizza
     *
     * @param pizza Pizza Entry where the price is extracted from
     * @param size  the Size of the Pizza (1-4 as little - family)
     * @throws AddingRegisterEntryException When adding the entry gone wrong
     */
    private void addRegisterEntry(Pizza pizza, PizzaSize size) throws AddingRegisterEntryException, InvalidEntryException, IOException, NoSuchEntryException {
        OrderedPizza bp = new OrderedPizza(pizza.getName());
        bp.setGroeße(size);
        bp.setPreis(pizza.getPreisKlein().orElse(0.0));
        addRegisterEntry(bp);

    }

    private void loadKassenEintraege() throws IOException, NoSuchEntryException {
        for (RegisterEntry e : registryadministration.getKassenEintraege()) {
            if (e instanceof OrderedPizza)
                addRegisterEntry((OrderedPizza) e);
        }
    }
}
