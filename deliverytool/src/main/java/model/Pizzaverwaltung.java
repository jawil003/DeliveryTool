package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Pizzaverwaltung {
    LinkedList<Pizza> pizzen = new LinkedList<>();
    Connection connection;

    //Constructors:

    public Pizzaverwaltung()
            throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        this(null);
    }

    public Pizzaverwaltung(LinkedList<Pizza> pizzen)
            throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (pizzen != null) {
            this.pizzen = pizzen;
        } else {
            this.pizzen = new LinkedList<>();
        }

        connection = SQLConnect.establishConnection();

        if (connection == null) {
            new SQLException("No connection to mySQL Server");
            return;
        }

        final Statement statement = connection.createStatement();
        final ResultSet resultSet =
                statement.executeQuery(
                        "SELECT `Name`, `PreisKlein`, `PreisMittel`, `PreisGroß`, `PreisFamilie` FROM Pizza");


        while (resultSet.next()) {
            this.pizzen.add(
                    new Pizza(
                            resultSet.getString(1),
                            null,
                            resultSet.getDouble(2),
                            resultSet.getDouble(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5)));
        }

        connection.close();
    }

    //getters and setters:

    public LinkedList<Pizza> getPizzen() {
        return pizzen;
    }

    public void setPizzen(LinkedList<Pizza> pizzen) {
        this.pizzen = pizzen;
    }

    public boolean isConnected() {
        return connection != null;
    }
}
