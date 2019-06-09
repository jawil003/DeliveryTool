/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WindowControllerTest {
    WindowController controller = new WindowController();

    @BeforeEach
    public void init() throws IOException {
        /**
         *@todo FXMLLoading error
         *@body Loading of FXML File fails because path is different than when calling same method from JavaFXApplication
         */
        controller.loadFXMLItemsAgain();
    }

    @Test
    public void allesLoeschenItemNotNull() throws IOException {
        assertNotNull(controller.getAllesLoeschenItem());
    }

    @Test
    public void ausgewaehltLoeschenItemNotNull() {
        assertNotNull(controller.getAusgewaehltLoeschen());
    }

    @Test
    public void schließenItemNotNull() {
        assertNotNull(controller.getSchließenItem());
    }

    @Test
    public void neustartItemNotNull() {
        assertNotNull(controller.getNeustartItem());
    }

    @Test
    public void bonDruckenItemNotNull() {
        assertNotNull(controller.getBonDruckenItem());
    }

    @Test
    public void eintragHinzufuegenItemNotNull() {
        assertNotNull(controller.getEintragHinzufuegenItem());
    }

    @Test
    public void pizzenListviewNotNull() {
        assertNotNull(controller.getPizzenListview());
    }

    @Test
    public void kassenListviewNotNull() {
        assertNotNull(controller.getKasseListview());
    }


}
