/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import DatabaseConnection.MySQLConnectHibernate;
import DatabaseConnection.SQLConnection;
import Model.Kasse.NoSuchEntryException;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PizzaIngredientConnectionAdministration {
    private static PizzaIngredientConnectionAdministration pizzaIngredientConnectionAdministration;
    private SetMultimap<Pizza, Ingredient> connections;
    private SQLConnection connection;

    private PizzaIngredientConnectionAdministration() {
        connection = MySQLConnectHibernate.getInstance();
        connections = LinkedHashMultimap.create();
        loadEntriesFromDB();
    }

    public static PizzaIngredientConnectionAdministration getInstance() {
        if (pizzaIngredientConnectionAdministration == null) {
            pizzaIngredientConnectionAdministration = new PizzaIngredientConnectionAdministration();
        }

        return pizzaIngredientConnectionAdministration;
    }

    private void loadEntriesFromDB() {
        for (Pizza p : PizzaAdministration.getInstance().getList()) {
            final List<Ingredient> ingredientConnectionsByPizzaId = connection.getIngredientConnectionsByPizzaId(p.getId());

            for (Ingredient e : ingredientConnectionsByPizzaId) {
                connections.put(p, e);
            }
        }
    }

    public Set<Ingredient> getIngrediencesByPizzaId(long pizzaId) throws NoSuchEntryException {
        final PizzaAdministration instance = PizzaAdministration.getInstance();
        final Pizza pizzaById = instance.getPizzaById(pizzaId);
        return connections.get(pizzaById);
    }
}
