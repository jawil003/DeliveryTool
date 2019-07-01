/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package DatabaseConnection;

import Model.PizzenDB.Ingredient;
import Model.PizzenDB.Pizza;

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

    boolean isRunning();

    List<Ingredient> getZutaten();
}
