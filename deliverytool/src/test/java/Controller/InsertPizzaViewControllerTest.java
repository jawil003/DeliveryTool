/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Controller;

import Model.Pizzen.Ingredientsadministration;
import Model.Pizzen.Pizza;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;

/**
 * @author Jannik Will
 * @version 1.3
 */

public class InsertPizzaViewControllerTest extends ApplicationTest {
    InsertPizzaViewController controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new InsertPizzaViewController(new Pizza(), Ingredientsadministration.getInstance());
        controller.loadFXMLItemsAgain();
    }

    @Test
    public void nameFieldNotNull() {
        assertNotNull(controller.getNameField());
    }

    @Test
    public void preisKleinFieldNotNull() {
        assertNotNull(controller.getPreisKleinField());
    }

    @Test
    public void preisMittelFieldNotNull() {
        assertNotNull(controller.getPreisMittelField());
    }

    @Test
    public void preisGrossFieldNotNull() {
        assertNotNull(controller.getPreisGrossField());
    }

    @Test
    public void preisFamilieFieldNotNull() {
        assertNotNull(controller.getPreisFamilieField());
    }

    @Test
    public void zutatenViewNotNull() {
        assertNotNull(controller.getZutatenView());
    }

    @Test
    public void hinzugefuegteZutatenViewNotNull() {
        assertNotNull(controller.getHinzugefuegteZutatenView());
    }

    @Test
    public void abbrechenButtonNotNull() {
        assertNotNull(controller.getAbbrechenButton());
    }

    @Test
    public void okButtonNotNull() {
        assertNotNull(controller.getOkButton());
    }


}