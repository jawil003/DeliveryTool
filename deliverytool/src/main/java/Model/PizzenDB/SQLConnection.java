/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import java.sql.SQLException;
import java.util.List;

public interface SQLConnection {

    List getPizzen() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;

    void deletePizza(Pizza p) throws SQLException;

    default void addPizza(Pizza pizza) throws SQLException {

    }

    default boolean isRunning() {
        return false;
    }

}
