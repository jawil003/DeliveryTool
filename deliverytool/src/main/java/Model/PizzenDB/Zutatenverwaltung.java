/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import Model.PizzenDB.SQLConnectionClasses.MySQL.MySQLConnectHibernate;

import java.util.LinkedList;
import java.util.List;

public class Zutatenverwaltung {
    private LinkedList<Zutat> zutaten;
    private SQLConnection sqlConnection;

    public Zutatenverwaltung() {
        this.zutaten = new LinkedList<>();

    }

    private List<Zutat> loadDBEntries() {
        sqlConnection = new MySQLConnectHibernate();
        return sqlConnection.getZutaten();
    }

    public void add(Zutat e) {
        zutaten.add(e);
    }
}
