/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import Model.PizzenDB.SQLConnectionClasses.MySQL.MySQLConnectHibernate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


/**
 *
 */
public class Ingredientsadministration {
    private ObservableList<Ingredient> zutaten;
    private SQLConnection sqlConnection;

    /**
     *
     */
    public Ingredientsadministration() {
        this.zutaten = FXCollections.observableArrayList();

    }

    /**
     *
     */
    private void loadDBEntries() {
        sqlConnection = new MySQLConnectHibernate();
        zutaten = FXCollections.observableArrayList(sqlConnection.getZutaten());
    }

    /**
     * @param e
     */
    public void add(Ingredient e) {
        if (zutaten.contains(e)) {
            return;
        }
        zutaten.add(e);
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
