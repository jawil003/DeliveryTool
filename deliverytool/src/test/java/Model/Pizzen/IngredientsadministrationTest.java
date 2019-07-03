/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class IngredientsadministrationTest {
    private Ingredientsadministration ingredientsadministration;

    @Test
    public void addZutaten() {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.deleteAll();
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Oregano"));
        ingredientsadministration.add(new Ingredient("Kümmel"));
        ingredientsadministration.add(new Ingredient("Tabasco"));
        ingredientsadministration.add(new Ingredient("Käse"));
        assertEquals(5, ingredientsadministration.getSize());
    }

    @Test
    public void addNull() {
        ingredientsadministration = Ingredientsadministration.getInstance();
    }

    @Test
    public void addZutatNull() {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.add(new Ingredient(null));
    }

    @Test
    public void addZutatenDuplicate() {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));

        assertEquals(1, ingredientsadministration.getSize());
    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.delete(-1);
    }

    @Test
    public void addAndDeleteAllPizzenEachByEach() throws Exception {
        addZutaten();

        for (int i = ingredientsadministration.getSize() - 1; i >= 0; i--) {
            ingredientsadministration.delete(i);
        }
        assertEquals(0, ingredientsadministration.getSize());
    }

    @Test
    public void addAndDelete() throws Exception {
        addZutaten();
        ingredientsadministration.deleteAll();

        assertEquals(0, ingredientsadministration.getSize());

    }
}
