/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import DatabaseConnection.MySQLConnectHibernate;
import DatabaseConnection.SQLConnection;
import Model.Kasse.NoSuchEntryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Jannik Will
 * @version 1.8
 */

@Slf4j
public class Ingredientsadministration {
    private ObservableList<Ingredient> zutaten;
    private SQLConnection sqlConnection;
    private static Ingredientsadministration ingredientsadministration;

    /**
     *
     */
    private Ingredientsadministration() {
        connectToDB();
        zutaten = FXCollections.observableArrayList(sqlConnection.getZutaten());

    }

    public static Ingredientsadministration getInstance() {
        if (ingredientsadministration == null) {
            ingredientsadministration = new Ingredientsadministration();
        }

        return ingredientsadministration;
    }

    /**
     *
     */
    public void connectToDB() throws ServiceException {
        sqlConnection = MySQLConnectHibernate.getInstance();
    }

    public boolean contains(Ingredient ingredient) {
        return zutaten.contains(ingredient);
    }

    /**
     * @param e
     */
    public void add(Ingredient e) {
        if (zutaten.contains(e)) {
            return;
        }
        zutaten.add(e);
        log.info("Added Ingredience={}", e.getName());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            sqlConnection.addIngredience(e);
        });
    }

    public ObservableList<Ingredient> getList() {
        return FXCollections.unmodifiableObservableList(zutaten);
    }

    /**
     * @param zutaten
     */
    public void addAll(List<Ingredient> zutaten) {
        this.zutaten.addAll(zutaten);
    }

    /**
     * @param zutaten
     */
    public void addAll(ObservableList<Ingredient> zutaten) {
        assert false;
        this.zutaten.addAll(zutaten);
    }

    /**
     * @param number
     */
    public void delete(int number) {
        if (number >= 0) {
            final Ingredient remove = zutaten.remove(number);
            log.info("Removed Ingredience={} by number.", remove);
        }

    }

    /**
     * @param ingredient
     */
    public void delete(Ingredient ingredient) {
        zutaten.remove(ingredient);
        log.info("Removed Ingredience={} by instance.", ingredient.getName());
    }
    /**
     *
     */
    public void deleteAll() {
        zutaten.clear();
    }

    /**
     * @param ingredientId
     * @return
     */
    public Ingredient get(long ingredientId) throws NoSuchEntryException {
        for (Ingredient e : zutaten) {
            if (e.getId() == ingredientId) {
                return e;
            }
        }

        throw new NoSuchEntryException("There is no such Ingredient Entry");
    }

    /**
     * @return
     */
    public int getSize() {
        return zutaten.size();
    }
}
