/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientsadministrationTest {
    private Ingredientsadministration ingredientsadministration;

    @Test
    public void addZutaten() {
        ingredientsadministration = new Ingredientsadministration();
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Oregano"));
        ingredientsadministration.add(new Ingredient("Kümmel"));
        ingredientsadministration.add(new Ingredient("Tabasco"));
        ingredientsadministration.add(new Ingredient("Käse"));
        assertEquals(5, ingredientsadministration.getSize());
    }

    @Test
    public void addNull() {
        ingredientsadministration = new Ingredientsadministration();
        ingredientsadministration.add(null);
    }

    @Test
    public void addZutatNull() {
        ingredientsadministration = new Ingredientsadministration();
        ingredientsadministration.add(new Ingredient(null));
    }

    @Test
    public void addZutatenDuplicate() {
        ingredientsadministration = new Ingredientsadministration();
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));

        assertEquals(1, ingredientsadministration.getSize());
    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ingredientsadministration = new Ingredientsadministration();
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
