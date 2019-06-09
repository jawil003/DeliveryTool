/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Controller;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WindowControllerTest extends ApplicationTest {
    WindowController controller;


    @Start
    public void start(Stage primaryStage) throws Exception {
        controller = new WindowController();
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
