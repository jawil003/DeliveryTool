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
        if (pizzen != null) {
            this.pizzen = FXCollections.observableArrayList(pizzen);
        } else {
            this.pizzen = FXCollections.observableArrayList();
            {
            }
        }

        sqlConnection = new SQLConnect();
        this.pizzen = FXCollections.observableArrayList(sqlConnection.getPizzen());
        this.pizzen.sort(Comparator.comparing(ListenEintrag::getName));
    }

    //getters and setters:

    public ObservableList<Pizza> getPizzen() {
        return pizzen;
    }

    public void add(Pizza pizza) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.pizzen.add(pizza);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sqlConnection = null;
                try {
                    sqlConnection = new SQLConnect();
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                try {
                    sqlConnection.setPizza(pizza);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public SQLConnect getSqlConnection() {
        return sqlConnection;
    }
}
