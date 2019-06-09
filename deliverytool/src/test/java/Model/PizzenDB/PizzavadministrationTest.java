/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PizzavadministrationTest {

    Pizzavadministration pizzavadministration;

    @Test
    public void addPizzen() throws Exception {
        pizzavadministration = new Pizzavadministration();
        pizzavadministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzavadministration.add(new Pizza("Schinken", 5.0, 10.0, 15.0, 20.0));
        pizzavadministration.add(new Pizza("Hawaii", 5.0, 10.0, 15.0, 20.0));
        pizzavadministration.add(new Pizza("Spaghetti", 5.0, 10.0, 15.0, 20.0));
        pizzavadministration.add(new Pizza("Margaritha", 5.0, 10.0, 15.0, 20.0));

        assertEquals(5, pizzavadministration.getSize());

    }

    @Test
    public void addPizzenDuplicate() {
        pizzavadministration = new Pizzavadministration();
        pizzavadministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzavadministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzavadministration.add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));

        assertEquals(1, pizzavadministration.getSize());

    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        pizzavadministration = new Pizzavadministration();
        pizzavadministration.delete(-1);
    }

    @Test
    public void addAndDeleteAllPizzenEachByEach() throws Exception {
        addPizzen();

        for (int i = 0; i < pizzavadministration.getSize(); i++) {
            pizzavadministration.delete(i);
        }
        assertEquals(0, pizzavadministration.getSize());
    }

    @Test
    public void addAndDelete() throws Exception {
        addPizzen();
        pizzavadministration.deleteAll();

        assertEquals(0, pizzavadministration.getSize());

    }

}