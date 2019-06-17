/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import Controller.PizzaSize;
import org.jetbrains.annotations.NotNull;

public class OrderedPizza extends RegisterEntry implements Comparable<RegisterEntry> {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private PizzaSize groeße;

    /**
     * Constructor where all values are empty or zero
     */
    public OrderedPizza() {
        this("", 0.0, PizzaSize.Small);

    }

    /**
     * @param name
     */
    public OrderedPizza(@NotNull String name) {
        super(name);
    }

    /**
     * @param name
     * @param preis
     * @param size
     */
    public OrderedPizza(@NotNull String name, @NotNull Double preis, @NotNull PizzaSize size) {
        super(name, preis);
        this.groeße = groeße;
    }

    /**
     * @return the Size which this Pizza has
     */
    public PizzaSize getGroeße() {
        return groeße;
    }

    public void setGroeße(@NotNull PizzaSize groeße) throws InvalidEntryException {
        this.groeße = groeße;
    }

    /**
     * @return the Parameters as String Collection
     */
    @Override
    public String toString() {
        return super.toString();
    }


    public String toEuroValue() {
        return String.format("%.2f", getPreis()) + "€";
    }

    /**
     * @param o
     * @return true (if the values of both compared OrderedPizza is matching), else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderedPizza that = (OrderedPizza) o;
        return groeße == that.groeße;
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
    public int compareTo(@NotNull RegisterEntry o) {
        if(o instanceof OrderedPizza){
            return super.compareTo(o) + (groeße.compareTo(((OrderedPizza) o).getGroeße()));
        }else {
            return super.compareTo(o);
        }
    }
}
