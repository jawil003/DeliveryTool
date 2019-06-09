/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import Model.PizzenDB.SQLConnectionClasses.MySQL.MySQLConnectHibernate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzavadministration {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private ObservableList<Pizza> pizzen;
    private SQLConnection sqlConnection;

    // Constructors:

    /**
     * This Constructor set the Pizzen List to zero (init empty)
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public Pizzavadministration() {
        this(new LinkedList<>());
    }

    /**
     * @param pizzen
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    private Pizzavadministration(LinkedList<Pizza> pizzen) {

            this.pizzen = FXCollections.observableArrayList(pizzen);
    }

    public void connectToDB() {
        //pizzen are loaded out of the mysql database with the help of the heping class MySQLConnect
        sqlConnection = new MySQLConnectHibernate();
        this.pizzen = FXCollections.observableArrayList(sqlConnection.getPizzen());
        this.pizzen.sort(Comparator.comparing(ListEntry::getName));
    }

    // method to add a new Pizza Entry (used by the eintraegeHinzufuegen Window)

    /**
     * Add a new Pizza to database annd to list
     *
     * @param pizza
     */
    public void add(Pizza pizza) {
        if (pizza == null | pizzen.contains(pizza)) {
            return;
        }

        this.pizzen.add(pizza);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            sqlConnection = null;
            sqlConnection = new MySQLConnectHibernate();
                sqlConnection.addPizza(pizza);

        });

    }

    /**
     * Delete a Entry in the Pizzavadministration
     *
     * @param number
     * @return
     */
    public void delete(int number) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (number < 0) {
            return;
        }
        final Pizza remove = pizzen.remove(number);
        if (sqlConnection != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        sqlConnection = new MySQLConnectHibernate();
                        sqlConnection.deletePizza(remove);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void deleteAll() {
        pizzen.clear();
    }

    // getters and setters:

    public int getSize() {
        return pizzen.size();
    }

    public ObservableList<Pizza> getList() {
        return FXCollections.unmodifiableObservableList(pizzen);
    }

    /**
     * @return the mySQL db connection
     */
    public SQLConnection getSqlConnection() {
        return sqlConnection;
    }
}
