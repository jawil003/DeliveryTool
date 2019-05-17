package Model;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;

public class Pizzaverwaltung {
    private LinkedList<Pizza> pizzen;

    //Constructors:

    public Pizzaverwaltung()
            throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        this(null);
    }

    private Pizzaverwaltung(LinkedList<Pizza> pizzen)
            throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (pizzen != null) {
            this.pizzen = pizzen;
        } else {
            this.pizzen = new LinkedList<>();
        }

        SQLConnect conn = new SQLConnect();
        this.pizzen = conn.getPizzen();
        this.pizzen.sort(Comparator.comparing(ListenEintrag::getName));
    }

    //getters and setters:

    public LinkedList<Pizza> getPizzen() {
        return pizzen;
    }

    public void add(Pizza pizza) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.pizzen.add(pizza);
        SQLConnect connect = new SQLConnect();
        connect.setPizza(pizza);
    }

}
