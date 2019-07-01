/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import DatabaseConnection.MySQLConnectHibernate;
import DatabaseConnection.SQLConnection;
import Model.Kasse.NoSuchEntryException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class PizzaAdministration {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private ObservableList<Pizza> pizzen;
    private SQLConnection sqlConnection;
    private Logger logger;

    // Constructors:

    /**
     * This Constructor set the Pizzen List to zero (init empty)
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public PizzaAdministration() {
        this(new LinkedList<>());
        logger = LoggerFactory.getLogger(this.getClass());
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
        logger.info("Added {} with {}€, {}€, {}€, {}€", pizza.getName(), pizza.getSmallPrice(),
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
        logger.info("Removed {} with {}€, {}€, {}€, {}€", pizza.getName(), pizza.getSmallPrice(),
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
            logger.info("Remove all:");
            logger.info("Removed {} with {}€, {}€, {}€, {}€", pizza.getName(), pizza.getSmallPrice(),
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
