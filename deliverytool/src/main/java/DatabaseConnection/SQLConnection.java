/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package DatabaseConnection;

import Model.Pizzen.Ingredient;
import Model.Pizzen.Pizza;
import Model.Pizzen.PizzaIngredientConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Jannik Will
 * @version 1.5
 */

public interface SQLConnection {

    List<Pizza> getPizzas();

    void deletePizza(Pizza p) throws SQLException;

    void addPizza(Pizza pizza);

    void addIngredience(Ingredient e);

    void setIngredientConnection(PizzaIngredientConnection connection);

    boolean isRunning();

    List<Ingredient> getZutaten();

    List<Ingredient> getIngredientConnectionsByPizzaId(long pizzaId);
}
