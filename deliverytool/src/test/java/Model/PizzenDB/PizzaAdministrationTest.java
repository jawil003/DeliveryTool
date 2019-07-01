/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jannik Will
 * @version 1.0
 */

class PizzaAdministrationTest {

    PizzaAdministration pizzaAdministration;

    @Test
    public void addPizzen() throws Exception {
        pizzaAdministration = new PizzaAdministration();
        pizzaAdministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzaAdministration.add(new Pizza("Schinken", 5.0, 10.0, 15.0, 20.0));
        pizzaAdministration.add(new Pizza("Hawaii", 5.0, 10.0, 15.0, 20.0));
        pizzaAdministration.add(new Pizza("Spaghetti", 5.0, 10.0, 15.0, 20.0));
        pizzaAdministration.add(new Pizza("Margaritha", 5.0, 10.0, 15.0, 20.0));

        assertEquals(5, pizzaAdministration.getSize());

    }

    @Test
    public void addPizzenDuplicate() {
        pizzaAdministration = new PizzaAdministration();
        pizzaAdministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzaAdministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzaAdministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));

        assertEquals(1, pizzaAdministration.getSize());

    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        pizzaAdministration = new PizzaAdministration();
        pizzaAdministration.delete(-1);
    }

    @Test
    public void addAndDeleteAllPizzenEachByEach() throws Exception {
        addPizzen();

        for (int i = pizzaAdministration.getSize() - 1; i >= 0; i--) {
            pizzaAdministration.delete(i);
        }

        assertEquals(0, pizzaAdministration.getSize());
    }

    @Test
    public void addAndDelete() throws Exception {
        addPizzen();
        pizzaAdministration.deleteAll();

        assertEquals(0, pizzaAdministration.getSize());

    }

}