/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZutatenverwaltungTest {
    private Zutatenverwaltung zutatenverwaltung;

    @Test
    public void addZutaten() {
        zutatenverwaltung = new Zutatenverwaltung();
        zutatenverwaltung.add(new Zutat("Petersilie"));
        zutatenverwaltung.add(new Zutat("Oregano"));
        zutatenverwaltung.add(new Zutat("Kümmel"));
        zutatenverwaltung.add(new Zutat("Tabasco"));
        zutatenverwaltung.add(new Zutat("Käse"));
        assertEquals(5, zutatenverwaltung.getSize());
    }

    @Test
    public void addNull() {
        zutatenverwaltung = new Zutatenverwaltung();
        zutatenverwaltung.add(null);
    }

    @Test
    public void addZutatNull() {
        zutatenverwaltung = new Zutatenverwaltung();
        zutatenverwaltung.add(new Zutat(null));
    }

    @Test
    public void addZutatenDuplicate() {
        zutatenverwaltung = new Zutatenverwaltung();
        zutatenverwaltung.add(new Zutat("Petersilie"));
        zutatenverwaltung.add(new Zutat("Petersilie"));
        zutatenverwaltung.add(new Zutat("Petersilie"));
        zutatenverwaltung.add(new Zutat("Petersilie"));
        zutatenverwaltung.add(new Zutat("Petersilie"));

        assertEquals(1, zutatenverwaltung.getSize());
    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        zutatenverwaltung = new Zutatenverwaltung();
        zutatenverwaltung.delete(-1);
    }

    @Test
    public void addAndDeleteAllPizzenEachByEach() throws Exception {
        addZutaten();

        for (int i = 0; i < zutatenverwaltung.getSize(); i++) {
            zutatenverwaltung.delete(i);
        }
        assertEquals(0, zutatenverwaltung.getSize());
    }

    @Test
    public void addAndDelete() throws Exception {
        addZutaten();
        zutatenverwaltung.deleteAll();

        assertEquals(0, zutatenverwaltung.getSize());

    }
}
