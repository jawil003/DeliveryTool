package model;

import java.util.LinkedList;
import java.util.Optional;

public class Pizza extends ListenEintrag {
    Optional<Double> preisKlein;
    Optional<Double> preisMittel;
    Optional<Double> preisGroß;
    private LinkedList<Zutat> zutaten;

    //Constructors:

    public Pizza(String name, LinkedList<Zutat> zutaten) {
        this(name, zutaten, null, null, null);
    }

    public Pizza(String name, LinkedList<Zutat> zutaten, Double preisKlein, Double preisMittel, Double preisGroß) {
        super(name);
        this.zutaten = zutaten;
        this.preisKlein = Optional.ofNullable(preisKlein);
        this.preisMittel = Optional.ofNullable(preisMittel);
        this.preisGroß = Optional.ofNullable(preisGroß);
    }

    //Getters and Setters:

    public LinkedList<Zutat> getZutaten() {
        return zutaten;
    }

    public void addZutat(Zutat zutat) {
        if (this.zutaten == null) {
            this.zutaten = new LinkedList<>();

        }

        this.zutaten.add(zutat);
    }

    public Optional<Double> getPreisKlein() {
        return preisKlein;
    }

    public void setPreisKlein(Double preisKlein) {
        this.preisKlein = Optional.ofNullable(preisKlein);
    }

    public Optional<Double> getPreisMittel() {
        return preisMittel;
    }

    public void setPreisMittel(Double preisMittel) {
        this.preisMittel = Optional.ofNullable(preisMittel);
    }

    public Optional<Double> getPreisGroß() {
        return preisGroß;
    }

    public void setPreisGroß(Double preisGroß) {
        this.preisGroß = Optional.ofNullable(preisGroß);
    }

    public void addZutaten(Zutat e) {
        zutaten.add(e);
    }
}
