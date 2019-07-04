/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.fail;


/**
 * @author Jannik Will
 * @version 1.0
 */

public class PizzaIngredientConnectionAdministrationTest {
    private static PizzaIngredientConnectionAdministration pizzaIngredientConnectionAdministration;
    private static PizzaAdministration pizzaAdministration;

    @BeforeClass
    public static void prepare() {
        pizzaAdministration = PizzaAdministration.getInstance();
        pizzaIngredientConnectionAdministration = PizzaIngredientConnectionAdministration.getInstance();
    }

    //WARNING: Just an test for Relation not an actual JUnitTest
    @Test
    @SneakyThrows
    public void addPizzaIngredience() {

    }

    @Test
    @SneakyThrows
    public void loadEntries() {
        final Set<Ingredient> ingrediencesByPizzaId = pizzaIngredientConnectionAdministration.getIngrediencesByPizzaId(1);
        System.out.println(ingrediencesByPizzaId);
        assert (ingrediencesByPizzaId.size() >= 1);
    }

    @Test
    @SneakyThrows
    public void loadEntriesFromUnvalidId() {
        try {
            pizzaIngredientConnectionAdministration.getIngrediencesByPizzaId(-1);
        } catch (PizzaIngredientConnectionAdministration.IdOutOfRangeException e) {
            return;
        }

        fail("Negative Id isn't catched properly");
    }
}
