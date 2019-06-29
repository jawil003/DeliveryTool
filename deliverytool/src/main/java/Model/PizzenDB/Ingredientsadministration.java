/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import Model.PizzenDB.SQLConnectionClasses.MySQL.MySQLConnectHibernate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 */
@Slf4j
public class Ingredientsadministration {
    private ObservableList<Ingredient> zutaten;
    private SQLConnection sqlConnection;
    private static Ingredientsadministration ingredientsadministration;
    private Logger logger;

    /**
     *
     */
    private Ingredientsadministration() {
        this.zutaten = FXCollections.observableArrayList();
        logger = LoggerFactory.getLogger(this.getClass());

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
    public void loadDBEntries() throws ServiceException {
        sqlConnection = new MySQLConnectHibernate();
        for (Ingredient e : sqlConnection.getZutaten()) {
            add(e);
        }

    }

    /**
     * @param e
     */
    public void add(Ingredient e) {
        if (zutaten.contains(e)) {
            return;
        }
        zutaten.add(e);
        logger.info("Added Ingredience={}", e.getName());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            sqlConnection = null;
            sqlConnection = new MySQLConnectHibernate();
            sqlConnection.addIngredience(e);

        });
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
            logger.info("Removed Ingredience={} by number.", remove);
        }

    }

    /**
     * @param ingredient
     */
    public void delete(Ingredient ingredient) {
        zutaten.remove(ingredient);
        logger.info("Removed Ingredience={} by instance.", ingredient.getName());
    }
    /**
     *
     */
    public void deleteAll() {
        zutaten.clear();
    }

    /**
     * @param number
     * @return
     */
    public Ingredient get(int number) {
        return zutaten.get(number);
    }

    /**
     * @return
     */
    public int getSize() {
        return zutaten.size();
    }
}
