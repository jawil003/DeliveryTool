/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PizzaverwaltungTest {

    Pizzaverwaltung pizzaverwaltung;

    @Test
    public void addPizzen() throws Exception {
        pizzaverwaltung = new Pizzaverwaltung();
        pizzaverwaltung.getPizzen().add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzaverwaltung.getPizzen().add(new Pizza("Schinken", 5.0, 10.0, 15.0, 20.0));
        pizzaverwaltung.getPizzen().add(new Pizza("Hawaii", 5.0, 10.0, 15.0, 20.0));
        pizzaverwaltung.getPizzen().add(new Pizza("Spaghetti", 5.0, 10.0, 15.0, 20.0));
        pizzaverwaltung.getPizzen().add(new Pizza("Margaritha", 5.0, 10.0, 15.0, 20.0));

        assertEquals(5, pizzaverwaltung.getPizzen().size());

    }

    @Test
    public void addPizzenDuplicate() {
        pizzaverwaltung = new Pizzaverwaltung();
        pizzaverwaltung.getPizzen().add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzaverwaltung.getPizzen().add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));
        pizzaverwaltung.getPizzen().add(new Pizza("Salami", 5.0, 10.0, 15.0, 20.0));

        assertEquals(1, pizzaverwaltung.getPizzen().size());

    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        pizzaverwaltung = new Pizzaverwaltung();
        pizzaverwaltung.delete(-1);
    }

    @Test
    public void addAndDeletePizzen() throws Exception {
        addPizzen();
        for (int i = 0; i < (pizzaverwaltung.getPizzen().size() - 1); i++) {
            Pizza remove = pizzaverwaltung.getPizzen().remove(i);
            assertNotEquals(remove, pizzaverwaltung.getPizzen().get(i));
        }
    }

}