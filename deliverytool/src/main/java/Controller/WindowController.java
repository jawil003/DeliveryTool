/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Controller;

import App.JavaFXApplication;
import Model.Kasse.*;
import Model.Pizzen.Ingredient;
import Model.Pizzen.Ingredientsadministration;
import Model.Pizzen.Pizza;
import Model.Pizzen.PizzaAdministration;
import Tools.LinkFetcher;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
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
    private MenuItem eintragHinzufuegenItem; //Pizza
    @FXML
    private MenuItem eintragHinzufuegenItem1; //Zutat
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
    private VBox mainVBox;
    @FXML
    private Label gesamterPreisLabel;

    //Other variables:
    private Stage primaryStage;
    private static final String FXML_CELLS_ROW_PIZZEN_LISTCELL_FXML = "deliverytool/Fxml/Cells/RowPizzenListcell.fxml";
    private static final String FXML_CELLS_ROW_KASSE_LISTCELL_FXML = "deliverytool/Fxml/Cells/RowKasseListcell.fxml";
    private static final String FXML_WINDOW_FXML = "deliverytool/Fxml/Window.fxml";
    private InsertPizzaViewController insertPizzaViewController;
    private RowPizzasController pizzasController;
    private PizzaAdministration pizzaAdministration;
    private Registryadministration registryadministration;
    private InsertNewIngredienceViewController insertNewIngredienceViewController;
    private Ingredientsadministration ingredientsadministration;
    private Parent pane;
    private Scene scene;

    /**
     */
    public WindowController() throws IOException {

        pizzaAdministration = PizzaAdministration.getInstance();
        registryadministration = Registryadministration.getInstance();
        ingredientsadministration = Ingredientsadministration.getInstance();
        loadFXMLItemsAgain();
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
      for (Pizza pizza : this.pizzaAdministration.getList()) {
          addPizzaRow(pizza);
          addListenerPizzaRow(pizza);
      }
  }

    /**
     * @throws MalformedURLException
     */
  private void addListener() throws MalformedURLException {

      pizzaAdministration.addListener(new PizzenViewListener());

      registryadministration.addListener(new RegistryAdministrationListener());

      // Actions:

      serviceAnsicht.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              WindowServiceController controller = null;
              try {
                  controller = new WindowServiceController();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              try {
                  controller.init(primaryStage);
              } catch (IOException | NoSuchEntryException e) {
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

      eintragHinzufuegenItem1.setOnAction(event -> {
          insertNewIngredienceViewController = null;
          try {
              insertNewIngredienceViewController = new InsertNewIngredienceViewController(primaryStage);
          } catch (MalformedURLException e) {
              e.printStackTrace();
          }

          try {
              insertNewIngredienceViewController.init();

              insertNewIngredienceViewController.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent event) {
                      ingredientsadministration.add(new Ingredient(insertNewIngredienceViewController.getNameTextField().getText()));
                      insertNewIngredienceViewController.getAbbrechenButton().fire();
                  }
              });
          } catch (IOException e) {
              e.printStackTrace();
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
          registryadministration.clear();
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

    private void autosizing() {
        mainVBox.layoutXProperty().bind(gridpane.widthProperty());
        pizzenListview.setPrefWidth(40);
        kasseListview.setPrefWidth(40);

        pizzenListview.layoutXProperty().bind(gridpane.widthProperty());
        kasseListview.layoutXProperty().bind(gridpane.widthProperty());
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
        private void eintragHinzufuegen() throws IOException, NoSuchEntryException {
            //FXMLLoader loader = new FXMLLoader(new File("deliverytool/Fxml/InsertNewPizzaView.fxml").toURI().toURL());
            Pizza pizza = new Pizza();
            final InsertPizzaViewController insertPizzaViewController = new InsertPizzaViewController(pizza, ingredientsadministration);
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
                    final OrderedPizza orderedPizza = registryadministration.get(selectedIndex);
                    final int size = registryadministration.getSize(orderedPizza);
                    if (size>1) {
                        final Label lookup = (Label) kasseListview.getItems().get(selectedIndex).lookup("#kasseAnzahlLabel");
                        lookup.setText(String.valueOf(Integer.valueOf(lookup.getText()) - 1));
                    } else if(size==1){
                        kasseListview.getItems().remove(selectedIndex);
                    }

                    registryadministration.remove(orderedPizza);
                    gesamterPreisLabel.setText(registryadministration.toEuroValue());
                    break;
                case PizzenListView:
                    break;
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
        mainVBox.widthProperty().addListener((observable, oldValue, newValue) -> gridpane.setPrefWidth(gridpane.getWidth() + (newValue.doubleValue() - oldValue.doubleValue())));
        mainVBox.heightProperty().addListener((observable, oldValue, newValue) -> gridpane.setPrefHeight(gridpane.getHeight() + (oldValue.doubleValue() - newValue.doubleValue())));
        gridpane.heightProperty().addListener((observable, oldValue, newValue) -> {
            kasseListview.setPrefHeight(kasseListview.getHeight() + (newValue.doubleValue() - oldValue.doubleValue()));
            pizzenListview.setPrefHeight(kasseListview.getHeight() + (newValue.doubleValue() - oldValue.doubleValue()));
        });

        gridpane.widthProperty().addListener((observable, oldValue, newValue) -> {
            kasseListview.setPrefWidth(kasseListview.getWidth() + (newValue.doubleValue() - oldValue.doubleValue()));
            kasseListview.setPrefWidth(kasseListview.getWidth() + (newValue.doubleValue() - oldValue.doubleValue()));
        });
    }

    // Listener:

    /**
     * Add a new Row for a choosed Pizza
     *
     * @param pizza Pizza Entry where the price is extracted from
     * @param size  the Size of the Pizza (1-4 as little - family)
     * @throws AddingOrderedPizzaException When adding the entry gone wrong
     */
    private void addOrderedPizza(Pizza pizza, PizzaSize size) throws AddingOrderedPizzaException, InvalidEntryException, IOException, NoSuchEntryException {
        OrderedPizza bp = new OrderedPizza(pizza);
        bp.setGroeße(size);
        addOrderedPizza(bp);

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

    /**
     * @param pizza The Entry for which a new RegisterRow is created
     * @throws IOException
     * @throws NoSuchEntryException
     */
    private void addOrderedPizza(OrderedPizza pizza) throws IOException, NoSuchEntryException {

        if (!registryadministration.contains(pizza)) {


            FXMLLoader loader = new FXMLLoader(new File(FXML_CELLS_ROW_KASSE_LISTCELL_FXML).toURI().toURL());

            RowRegisterController kasseContr = new RowRegisterController();
            loader.setController(kasseContr);

            Pane rootPane = loader.load();
            kasseContr.init(pizza);

            this.kasseListview.getItems().add(rootPane);
            registryadministration.add(pizza);
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
                    registryadministration.add(pizza);
                    return;
                }
            }
        }
    }

    private void readdOrderedPizza(OrderedPizza pizza) throws NoSuchEntryException, IOException {
        FXMLLoader loader = new FXMLLoader(new File(FXML_CELLS_ROW_KASSE_LISTCELL_FXML).toURI().toURL());

        RowRegisterController kasseContr = new RowRegisterController();
        loader.setController(kasseContr);

        Pane rootPane = loader.load();
        kasseContr.init(pizza);

        kasseContr.getKasseAnzahlLabel().setText(String.valueOf(registryadministration.getSize(pizza)));

        this.kasseListview.getItems().add(rootPane);
    }

    class InvalidInstanciationInsertPizzaViewController extends Exception {
        InvalidInstanciationInsertPizzaViewController(String message) {
            super(message);
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
                                addOrderedPizza(pizza, PizzaSize.Small);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

        pizzasController
                .getMittelButton()
                .setOnAction(
                        event -> {
                            try {
                                addOrderedPizza(pizza, PizzaSize.Middle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });


        pizzasController
                .getGrossButton()
                .setOnAction(
                        event -> {
                            try {
                                addOrderedPizza(pizza, PizzaSize.Big);
                            } catch (AddingOrderedPizzaException | IOException | InvalidEntryException | NoSuchEntryException e) {
                                e.printStackTrace();
                            }
                        });

        pizzasController
                .getFamilieButton()
                .setOnAction(
                        event -> {
                            try {
                                addOrderedPizza(pizza, PizzaSize.Family);
                            } catch (AddingOrderedPizzaException | InvalidEntryException | IOException | NoSuchEntryException e) {
                                e.printStackTrace();
                            }
                        });
    }

    private void reloadRegistryEntries() throws NoSuchEntryException, IOException {
        for (OrderedPizza e : registryadministration.getRegisterEntriesUnique()) {
            if (e instanceof OrderedPizza) {
                readdOrderedPizza((OrderedPizza) e);
            }
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
            try {
                eintragHinzufuegen();
            } catch (IOException | NoSuchEntryException e) {
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

    public PizzaAdministration getPizzaAdministration() {
        return pizzaAdministration;
    }

    public void setPizzaAdministration(PizzaAdministration pizzaAdministration) {
        this.pizzaAdministration = pizzaAdministration;
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

    private void loadKassenEintraege() throws IOException, NoSuchEntryException {
        for (OrderedPizza e : registryadministration.getRegisterEntriesUnique()) {
            if (e instanceof OrderedPizza)
                addOrderedPizza((OrderedPizza) e);
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
                BonCreator creator = new BonCreator(registryadministration, gesamterPreis, (chooser.showSaveDialog(primaryStage.getScene().getWindow())).getAbsolutePath());
                //creator.addPizzas(registryadministration.getRegisterEntriesUnique(), gesamterPreis);
                creator.close();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
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
                if (kasseListview.getItems().isEmpty()) {
                    reloadRegistryEntries();
                }
                if (pizzenListview.getItems().isEmpty()) {
                    loadPizzaEntries();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchEntryException e) {
                e.printStackTrace();
            }

            final ObservableList<Node> children = ((VBox) primaryStage.getScene().getRoot()).getChildren();
            children.remove(1);
            final Node node = pane.getChildrenUnmodifiable().get(1);
            children.add(node);


            primaryStage.show();
        }
    }

    class AddingOrderedPizzaException extends Exception {
        AddingOrderedPizzaException(String message) {
            super(message);
        }
    }

    /**
     * The Listener which is triggered when the OkButon is pressed
     */
    private class OkButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                pizzaAdministration.add(new Pizza(insertPizzaViewController.getNameField().getText(),
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

    private class RegistryAdministrationListener implements SetChangeListener<OrderedPizza> {
        /**
         * Called after a change has been made to an ObservableMap.
         * This method is called on every elementary change (put/remove) once.
         * This means, complex changes like keySet().removeAll(Collection) or clear()
         * may result in more than one call of onChanged method.
         *
         * @param change the change that was made
         */
        @Override
        public void onChanged(Change<? extends OrderedPizza> change) {
            gesamterPreisLabel.setText(registryadministration.toEuroValue());
        }
    }

    public Ingredientsadministration getIngredientsadministration() {
        return ingredientsadministration;
    }

    public void setIngredientsadministration(Ingredientsadministration ingredientsadministration) {
        this.ingredientsadministration = ingredientsadministration;
    }
}
