package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        SQLConnect connect = new SQLConnect();

        String sql;
        ResultSet set = connect.selectItems("SELECT * FROM Pizza");

        while (set.next()) {
            Pizza pizza = new Pizza(set.getString(1),
                    null, set.getDouble(2),
                    set.getDouble(3), set.getDouble(4),
                    set.getDouble(5));
            this.pizzen.add(pizza);
        }

        connect.closeConnection();
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
