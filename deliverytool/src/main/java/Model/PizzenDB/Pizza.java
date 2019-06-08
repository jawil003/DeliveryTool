/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;


public class Pizza extends ListenEintrag {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private String name;
    private Optional<Double> preisKlein;
    private Optional<Double> preisMittel;
    private Optional<Double> preisGroß;
    private Optional<Double> preisFamilie;
    private LinkedList<Zutat> zutaten;

    // Constructors:

    /**
     * The Constructor where all Parameters are set to zero or empty
     */
    public Pizza() {
        this("", new LinkedList<>());
    }

    public Pizza(String name, Double preisKlein,
                 Double preisMittel,
                 Double preisGroß,
                 Double preisFamilie) {
        this(name, new LinkedList<>(), preisKlein, preisMittel, preisGroß, preisGroß);
    }
    public Pizza(String name, LinkedList<Zutat> zutaten) {
        this(name, zutaten, null, null, null, null);
    }

    /**
     * @param name
     * @param zutaten
     * @param preisKlein
     * @param preisMittel
     * @param preisGroß
     * @param preisFamilie
     */
    public Pizza(
            String name,
            LinkedList<Zutat> zutaten,
            Double preisKlein,
            Double preisMittel,
            Double preisGroß,
            Double preisFamilie) {
        super(name);
        this.name = name;
        this.zutaten = zutaten;
        this.preisKlein = Optional.ofNullable(preisKlein);
        this.preisMittel = Optional.ofNullable(preisMittel);
        this.preisGroß = Optional.ofNullable(preisGroß);
        this.preisFamilie = Optional.ofNullable(preisFamilie);
    }

    // Getters and Setters:

    /**
     * @return the LinkedList of Zutat Items of this Pizza
     */
    public LinkedList<Zutat> getZutaten() {
        return zutaten;
    }

    /**
     * @param zutat
     */
    public void addZutat(Zutat zutat) {
        if (this.zutaten == null) {
            this.zutaten = new LinkedList<>();
        }

        this.zutaten.add(zutat);
    }

    /**
     * @return the Optional Double Value of a small Pizza
     */
    public Optional<Double> getPreisKlein() {
        return preisKlein;
    }

    /**
     * @param preisKlein
     */
    public void setPreisKlein(Double preisKlein) {
        this.preisKlein = Optional.ofNullable(preisKlein);
    }

    /**
     * @return the Optional Double Value of a middle Pizza
     */
    public Optional<Double> getPreisMittel() {
        return preisMittel;
    }

    /**
     * @param preisMittel
     */
    public void setPreisMittel(Double preisMittel) {
        this.preisMittel = Optional.ofNullable(preisMittel);
    }

    /**
     * @return the Optional Double Value of a big Pizza
     */
    public Optional<Double> getPreisGroß() {
        return preisGroß;
    }

    /** @param preisGroß */
    public void setPreisGroß(Double preisGroß) {
        this.preisGroß = Optional.ofNullable(preisGroß);
    }

    /**
     * @param e
     */
    public void addZutaten(Zutat e) {
        zutaten.add(e);
    }

    /**
     * @return the Optional Double Value of a family Pizza
     */
    public Optional<Double> getPreisFamilie() {
        return preisFamilie;
    }

    /**
     * @param preisFamilie
     */
    public void setPreisFamilie(Optional<Double> preisFamilie) {
        this.preisFamilie = preisFamilie;
    }

    /**
     * @return all parameters as String
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @param o
     * @return true (if the values of both compared KasseEintrag is matching), else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(name, pizza.name) &&
                Objects.equals(preisKlein, pizza.preisKlein) &&
                Objects.equals(preisMittel, pizza.preisMittel) &&
                Objects.equals(preisGroß, pizza.preisGroß) &&
                Objects.equals(preisFamilie, pizza.preisFamilie) &&
                Objects.equals(zutaten, pizza.zutaten);
    }

    /** @return the hashCode value */
    @Override
    public int hashCode() {
        return Objects.hash(name, preisKlein, preisMittel, preisGroß, preisFamilie, zutaten);
    }
}
