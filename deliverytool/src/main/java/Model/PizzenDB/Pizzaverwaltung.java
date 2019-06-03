/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzaverwaltung {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private ObservableList<Pizza> pizzen;
    private SQLConnect sqlConnection;

    // Constructors:

    /**
     * This Constructor set the Pizzen List to zero (init empty)
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public Pizzaverwaltung()
            throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        this(new LinkedList<>());
    }

    /**
     * @param pizzen
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    private Pizzaverwaltung(LinkedList<Pizza> pizzen)
            throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        //if there is no initialized list the constructor will initialize a new one

        if (pizzen == null) {
            this.pizzen = FXCollections.observableArrayList();
        }

        //pizzen are loaded out of the mysql database with the help of the heping class SQLConnect

        sqlConnection = new SQLConnect();
        this.pizzen = FXCollections.observableArrayList(sqlConnection.getPizzen());
        this.pizzen.sort(Comparator.comparing(ListenEintrag::getName));
    }

    // method to add a new Pizza Entry (used by the eintraegeHinzufuegen Window)

    /**
     * Add a new Pizza to database and to list
     *
     * @param pizza
     */
    public void add(Pizza pizza) {
        this.pizzen.add(pizza);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            sqlConnection = null;
            try {
                sqlConnection = new SQLConnect();
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                sqlConnection.addPizza(pizza);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * Delete a Entry in the Pizzaverwaltung
     *
     * @param number
     * @return
     */
    public void delete(int number) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        sqlConnection = new SQLConnect();
        final Pizza remove = pizzen.remove(number);
        sqlConnection.deletePizza(remove);


    }

    // getters and setters:

    /**
     * @return the Oberservable List with Pizza Entries
     */
    public ObservableList<Pizza> getPizzen() {
        return pizzen;
    }

    /**
     * @return the mySQL db connection
     */
    public SQLConnect getSqlConnection() {
        return sqlConnection;
    }
}
