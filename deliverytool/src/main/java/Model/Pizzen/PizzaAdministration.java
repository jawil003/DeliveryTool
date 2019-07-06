/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import DatabaseConnection.MySQLConnectHibernate;
import DatabaseConnection.SQLConnection;
import Model.Kasse.NoSuchEntryException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jannik Will
 * @version 1.8
 */

@Slf4j
public class PizzaAdministration {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private ObservableList<Pizza> pizzen;
    private SQLConnection sqlConnection;
    private static PizzaAdministration pizzaAdministration;

    // Constructors:

    /**
     * This Constructor set the Pizzen List to zero (init empty)
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private PizzaAdministration() {
        this(new LinkedList<>());
        connectToDB();
    }

    public static PizzaAdministration getInstance() {
        if (pizzaAdministration == null) {
            pizzaAdministration = new PizzaAdministration();
        }

        return pizzaAdministration;
    }

    /**
     * @param pizzen
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    private PizzaAdministration(LinkedList<Pizza> pizzen) {

        this.pizzen = FXCollections.observableArrayList(pizzen);
    }

    public void addListener(ListChangeListener e) {
        pizzen.addListener(e);
    }

    public void connectToDB() throws ServiceException {
        //pizzen are loaded out of the mysql database with the help of the heping class MySQLConnect
        sqlConnection = MySQLConnectHibernate.getInstance();
        this.pizzen = FXCollections.observableArrayList(sqlConnection.getPizzas());
    }

    public Pizza getPizzaById(long id) throws NoSuchEntryException {
        final ListIterator<Pizza> pizzaListIterator = pizzen.listIterator();

        while (pizzaListIterator.hasNext()) {
            final Pizza next = pizzaListIterator.next();

            if (next.getId() == id) {
                return next;
            }
        }

        throw new NoSuchEntryException("This Entry isn't stored");
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
        log.info("Added {} with {}€, {}€, {}€, {}€", pizza.getName(), pizza.getSmallPrice(),
                pizza.getMiddlePrice(), pizza.getBigPrice(), pizza.getFamilyPrice());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            sqlConnection.addPizza(pizza);

        });

    }

    public Pizza search(String name) throws NoSuchEntryException {
        for (Pizza e : pizzen) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        throw new NoSuchEntryException("No such Entry found");
    }

    public boolean contains(Pizza pizza) {
        return pizzen.contains(pizza);

    }

    /**
     * Delete a Entry in the PizzaAdministration
     *
     * @param number
     * @return
     */
    public void delete(int number) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (number < 0) {
            return;
        }
        final Pizza pizza = pizzen.remove(number);
        log.info("Removed {} with {}€, {}€, {}€, {}€", pizza.getName(), pizza.getSmallPrice(),
                pizza.getMiddlePrice(), pizza.getBigPrice(), pizza.getFamilyPrice());
        if (sqlConnection != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        sqlConnection = MySQLConnectHibernate.getInstance();
                        sqlConnection.deletePizza(pizza);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void deleteAll() {
        for (Pizza pizza : pizzen) {
            log.info("Remove all:");
            log.info("Removed {} with {}€, {}€, {}€, {}€", pizza.getName(), pizza.getSmallPrice(),
                    pizza.getMiddlePrice(), pizza.getBigPrice(), pizza.getFamilyPrice());
        }
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
