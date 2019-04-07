package model;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Pizzaverwaltung {
    LinkedList<Pizza> pizzen;

    public Pizzaverwaltung() throws SQLException {
        this(null);
    }

    public Pizzaverwaltung(LinkedList<Pizza> pizzen) throws SQLException {
        Connection connection = null;
        try {
            connection = SQLConnect.establishConnection("deliverytool.Pizza");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert connection != null;
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery("SELECT `Name`, `PreisKlein`, `PreisMittel`, `PreisGroß`, `PreisFamilie` FROM Pizza");
        //FIXME: NullPointerException caused by wrong table access.
        while (resultSet.next()) {
            pizzen.add(new Pizza(resultSet.getString(1),
                    null,
                    resultSet.getDouble(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)));
        }

        this.pizzen = pizzen;
    }

    public LinkedList<Pizza> getPizzen() {
        return pizzen;
    }

    public void setPizzen(LinkedList<Pizza> pizzen) {
        this.pizzen = pizzen;
    }
}
