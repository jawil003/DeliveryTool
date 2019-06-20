/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import Model.PizzenDB.SQLConnectionClasses.MySQL.MySQLConnectHibernate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 */
public class Ingredientsadministration {
    private ObservableList<Ingredient> zutaten;
    private SQLConnection sqlConnection;
    private static Ingredientsadministration ingredientsadministration;

    /**
     *
     */
    private Ingredientsadministration() {
        this.zutaten = FXCollections.observableArrayList();

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
    public void loadDBEntries() {
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
        if (number >= 0)
            zutaten.remove(number);
    }

    /**
     * @param ingredient
     */
    public void delete(Ingredient ingredient) {
        zutaten.remove(ingredient);
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
