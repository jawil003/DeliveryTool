package Model.PizzenDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzaverwaltung {
    private ObservableList<Pizza> pizzen;
    private SQLConnect sqlConnection;

    //Constructors:

    public Pizzaverwaltung()
            throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        this(null);
    }

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

    //method to add a new Pizza Entry (used by the eintraegeHinzufuegen Window)

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

    //getters and setters:

    public ObservableList<Pizza> getPizzen() {
        return pizzen;
    }

    public SQLConnect getSqlConnection() {
        return sqlConnection;
    }
}
