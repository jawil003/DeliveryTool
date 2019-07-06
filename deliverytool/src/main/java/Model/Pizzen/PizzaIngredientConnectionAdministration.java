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
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class PizzaIngredientConnectionAdministration {
    private static PizzaIngredientConnectionAdministration pizzaIngredientConnectionAdministration;
    private SetMultimap<Pizza, Ingredient> connections;
    private SQLConnection connection;
    private PizzaAdministration pizzaAdministration;
    private Ingredientsadministration ingredientsAdministration;

    private PizzaIngredientConnectionAdministration() {
        connection = MySQLConnectHibernate.getInstance();
        connections = LinkedHashMultimap.create();
        pizzaAdministration = PizzaAdministration.getInstance();
        ingredientsAdministration = Ingredientsadministration.getInstance();
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
                log.info("Loaded Connection Pizza=() and Ingredient={} from Database", p, e);
                connections.put(p, e);
            }
        }
    }

    public void setPizzaIngredientConnection(Pizza pizza, Ingredient ingredient) throws NoSuchEntryException {
        boolean contains = pizzaAdministration.contains(pizza);
        boolean contains1 = ingredientsAdministration.contains(ingredient);
        if (contains && contains1) {
            connections.put(pizza, ingredient);
            connection.setIngredientConnection(new PizzaIngredientConnection(pizza.getId(), ingredient.getId()));
            log.info("Set new Connection Pizza={} and Ingredient={}", pizza, ingredient);
        } else {
            if (!contains & !contains1) {
                throw new NoSuchEntryException("There is no Pizza and Ingredient with this Id in Database");
            } else if (!contains) {
                throw new NoSuchEntryException("There is no Pizza with this Id in Database");
            } else {
                throw new NoSuchEntryException("There is no Ingredient with this Id in Database");
            }
        }
    }

    public void setPizzaIngredientConnection(long pizzaId, long ingredientId) throws NoSuchEntryException {
        boolean containsPizza = true;
        boolean containsIngredient = true;
        Pizza pizza = null;
        Ingredient ingredient = null;
        try {
            pizza = pizzaAdministration.getPizzaById(pizzaId);
        } catch (NoSuchEntryException e) {
            containsPizza = false;
        }
        try {
            ingredient = ingredientsAdministration.get(ingredientId);
        } catch (NoSuchEntryException e) {
            containsIngredient = false;
        }

        if (!containsPizza & !containsIngredient) {
            throw new NoSuchEntryException("There are no such Pizza or Ingredient in Database");
        } else if (!containsPizza) {
            throw new NoSuchEntryException("There are no such Pizza in Database");
        } else if (!containsIngredient) {
            throw new NoSuchEntryException("There are no such Ingredient in Database");
        } else {
            connections.put(pizza, ingredient);
            connection.setIngredientConnection(new PizzaIngredientConnection(pizzaId, ingredientId));
            log.info("Set new Connection Pizza={} and Ingredient={}", pizza, ingredient);
        }
    }

    public Set<Ingredient> getIngrediencesByPizzaId(long pizzaId) throws NoSuchEntryException, IdOutOfRangeException {
        if (pizzaId >= 0) {

            Pizza pizzaById;
            try {
                pizzaById = pizzaAdministration.getPizzaById(pizzaId);
            } catch (NoSuchEntryException e) {
                NoSuchEntryException noSuchEntryException = new NoSuchEntryException("There is no Pizza with this Id in Database");
                log.debug("Error getting Ingrediences for PizzaId={}", pizzaId, noSuchEntryException);
                throw noSuchEntryException;
            }
            Set<Ingredient> ingredients = connections.get(pizzaById);
            log.debug("Getted Ingrediences={} for PizzaId={}", ingredients, pizzaById);
            return ingredients;

        } else {
            final IdOutOfRangeException idOutOfRangeException = new IdOutOfRangeException("This Index is unvalid, because it is negative");
            log.debug("Error getting Ingrediences for PizzaId={}", pizzaId, idOutOfRangeException);
            throw idOutOfRangeException;
        }
    }

    public class IdOutOfRangeException extends Exception {
        public IdOutOfRangeException(String message) {
            super(message);
        }
    }
}
