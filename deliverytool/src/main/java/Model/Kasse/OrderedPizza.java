/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Kasse;

import Controller.PizzaSize;
import Model.Pizzen.Pizza;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * @author Jannik Will
 * @version 1.3
 */
@NoArgsConstructor(force = true)
@Getter
@Entity
@Table(name = "RegisterEntries")
@EqualsAndHashCode(exclude = "id")
public class OrderedPizza implements Comparable<OrderedPizza> {

    @EmbeddedId
    private RegistryEntryPizzaID id;
    @ManyToOne
    @JoinColumns(
            @JoinColumn(name = "PizzaID", insertable = false, updatable = false)
    )
    private Pizza pizza;
    @NotNull
    @Setter
    @Column(name = "Pizza_Size")
    private PizzaSize groeße;
    @Setter
    @Column(name = "Pizza_Counter")
    private long counter;

    public OrderedPizza(long pizzaId) {
        this.id = new RegistryEntryPizzaID(pizzaId);
    }

    public OrderedPizza(Pizza p) {
        this(p.getId());
        pizza = p;
    }

    public String getName() {
        if (pizza == null) {
            return "";
        }
        return pizza.getName();
    }

    public double getPreis() {
        if (pizza == null) {
            return 0;
        }
        switch (groeße) {
            case Small:
                return pizza.getSmallPrice();
            case Middle:
                return pizza.getMiddlePrice();
            case Big:
                return pizza.getBigPrice();
            case Family:
                return pizza.getFamilyPrice();
        }

        return 0;
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
    public int compareTo(@NotNull OrderedPizza o) {
        int one = Integer.valueOf(String.valueOf(id.getPizzaId() + id.getRegistryEntryId()));
        int two = Integer.valueOf(String.valueOf(o.getId().getPizzaId() + o.getId().getPizzaId()));

        return one - two;
    }

}
