/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WindowControllerTest extends ApplicationTest {
    WindowController controller;


    @Start
    public void start(Stage primaryStage) throws Exception {
        controller = new WindowController();
        controller.loadFXMLItemsAgain();
    }

    /**
     * Tests wheater the AllesLöschen MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void allesLoeschenItemNotNull() {
        assertNotNull(controller.getAllesLoeschenItem());
    }

    /**
     * Tests wheater the AusgewaehltLöschen MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void ausgewaehltLoeschenItemNotNull() {
        assertNotNull(controller.getAusgewaehltLoeschen());
    }

    /**
     * Tests wheater the Schliessen MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void schließenItemNotNull() {
        assertNotNull(controller.getSchliessenItem());
    }

    /**
     * Tests wheater the Neustart MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void neustartItemNotNull() {
        assertNotNull(controller.getNeustartItem());
    }

    /**
     * Tests wheater the BonDrucken MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void bonDruckenItemNotNull() {
        assertNotNull(controller.getBonDruckenItem());
    }

    /**
     * Tests wheater the EintragHinzufzuegen MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void eintragHinzufuegenItemNotNull() {
        assertNotNull(controller.getEintragHinzufuegenItem());
    }

    /**
     * Tests wheater the PizzaenListView is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void pizzenListviewNotNull() {
        assertNotNull(controller.getPizzenListview());
    }

    /**
     * Tests wheater the KassenListView is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void kassenListviewNotNull() {
        assertNotNull(controller.getKasseListview());
    }

    /**
     * Tests wheater the Über MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void überMenuItemNotNull() {
        assertNotNull(controller.getUeberItem());
    }

    /**
     * Tests wheater the KasseAnsicht MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void KasseAnsichtItemNotNull() {
        assertNotNull(controller.getKasseAnsicht());
    }

    /**
     * Tests wheater the ZubereitungAnsicht MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void zubereitungAnsichtItemNotNull() {
        assertNotNull(controller.getZubereitungAnsicht());
    }

    /**
     * Tests wheater the ServiceAnsicht MenuItem is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void serviceAnsichtItemNotNull() {
        assertNotNull(controller.getServiceAnsicht());
    }

    /**
     * Tests wheater the GesamterPreis Label is Null, because the loading from the FXML File wasn´t sucessful
     */
    @Test
    public void gesamterPreisLabelNotNull() {
        assertNotNull(controller.getGesamterPreisLabel());
    }
}
