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
public class Zutatenverwaltung {
    private ObservableList<Zutat> zutaten;
    private SQLConnection sqlConnection;

    /**
     *
     */
    public Zutatenverwaltung() {
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
    public void add(Zutat e) {
        zutaten.add(e);
    }

    /**
     * @param zutaten
     */
    public void addAll(List<Zutat> zutaten) {
        this.zutaten.addAll(zutaten);
    }

    /**
     * @param zutaten
     */
    public void addAll(ObservableList<Zutat> zutaten) {
        assert false;
        this.zutaten.addAll(zutaten);
    }

    /**
     * @param number
     */
    public void delete(int number) {
        zutaten.remove(number);
    }

    /**
     * @param zutat
     */
    public void delete(Zutat zutat) {
        zutaten.remove(zutat);
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
    public Zutat get(int number) {
        return zutaten.get(number);
    }

    /**
     * @return
     */
    public int getSize() {
        return zutaten.size();
    }
}
