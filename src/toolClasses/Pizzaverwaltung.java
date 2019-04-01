package toolClasses;


import java.util.LinkedList;

public class Pizzaverwaltung {
    LinkedList<Pizza> pizzen;

    public Pizzaverwaltung() {
        this(null);
    }

    public Pizzaverwaltung(LinkedList<Pizza> pizzen) {
        this.pizzen = pizzen;
    }
}
