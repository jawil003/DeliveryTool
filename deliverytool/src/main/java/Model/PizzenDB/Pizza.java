/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;



import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;


public class Pizza extends ListEntry implements Comparable<Pizza> {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private String name;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Double> preisKlein;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Double> preisMittel;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Double> preisGroß;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Double> preisFamilie;
    private LinkedList<Ingredient> zutaten;

    // Constructors:

    /**
     * The Constructor where all Parameters are set to zero or empty
     */
    public Pizza() {
        this("", new LinkedList<>());
    }

    public Pizza(String name, LinkedList<Ingredient> zutaten) {
        this(name, zutaten, null, null, null, null);
    }

    public Pizza(String name, Double preisKlein,
                 Double preisMittel,
                 Double preisGroß,
                 Double preisFamilie) {
        this(name, new LinkedList<>(), preisKlein, preisMittel, preisGroß, preisGroß);
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
            LinkedList<Ingredient> zutaten,
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
     * @return the LinkedList of Ingredient Items of this Pizza
     */
    public LinkedList<Ingredient> getIngridience() {
        return zutaten;
    }

    /**
     * @param ingredient
     */
    public void addIngridience(Ingredient ingredient) {
        if (this.zutaten == null) {
            this.zutaten = new LinkedList<>();
        }

        this.zutaten.add(ingredient);
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
    public void addZutaten(Ingredient e) {
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
        return name + " (" + preisKlein.orElse(0.0) + ", " + preisMittel.orElse(0.0) + ", " + preisGroß.orElse(0.0) + ", " + preisFamilie.orElse(0.0) + ")" +
                "with Ingredience: " +
                zutaten;
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NotNull Pizza o) {
        return name.compareTo(o.name)
                + Integer.valueOf(String.valueOf(preisKlein.get() - o.getPreisKlein().get())
                + (preisMittel.get() - o.getPreisMittel().get())
                + (preisGroß.get() - o.getPreisGroß().get())
                + (preisFamilie.get() - o.getPreisFamilie().get()));
    }
}
