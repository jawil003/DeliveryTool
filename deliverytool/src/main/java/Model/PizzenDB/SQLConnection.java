/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import java.sql.SQLException;
import java.util.List;

public interface SQLConnection {

    List<Pizza> getPizzas();

    void deletePizza(Pizza p) throws SQLException;

    void addPizza(Pizza pizza);

    boolean isRunning();

    List<Ingredient> getZutaten();
}
