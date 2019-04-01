package toolClasses;

import java.util.LinkedList;

public class Pizza {
    Double preis;
    private String name;
    private LinkedList<Zutat> zutaten;

    //Constructors:

    public Pizza() {
        this("", null, 0.00);
    }

    public Pizza(String name, LinkedList<Zutat> zutaten, Double preis) {
        this.name = name;
        this.zutaten = zutaten;
        this.preis = preis;
    }

    //Getters and Setters:

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Zutat> getZutaten() {
        return zutaten;
    }

    public void addZutat(Zutat zutat) {
        if (this.zutaten == null) {
            this.zutaten = new LinkedList<>();

        }

        this.zutaten.add(zutat);
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }


}
