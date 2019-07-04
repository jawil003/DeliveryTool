/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Testing;

import Model.Pizzen.Ingredient;
import Model.Pizzen.PizzaAdministration;
import Model.Pizzen.PizzaIngredientConnectionAdministration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class PizzaRelationTest {
    private static PizzaIngredientConnectionAdministration pizzaIngredientConnectionAdministration;
    private static PizzaAdministration pizzaAdministration;

    @BeforeAll
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
        assert (ingrediencesByPizzaId.size() == 1);
    }

    @Test
    @SneakyThrows
    public void loadEntriesFromUnvalidId() {
        final Set<Ingredient> ingrediencesByPizzaId = pizzaIngredientConnectionAdministration.getIngrediencesByPizzaId(-1);
    }
}
