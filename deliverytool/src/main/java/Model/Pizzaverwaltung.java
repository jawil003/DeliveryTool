package Model;

import java.sql.Connection;
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

        final LinkedList<String> strings = connect.selectItems("Pizza", "Name", "PreisKlein",
                "PreisMittel", "PreisGroß", "PreisFamilie");


        for (int i = 0; i < strings.size() / 5; i++) {
            pizzen.add(new Pizza(strings.get(i), null, Double.parseDouble(strings.get(i + 1)),
                    Double.parseDouble(strings.get(i + 2)), Double.parseDouble(strings.get(i + 3)), Double.parseDouble(strings.get(i + 4))));
        }
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
