/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import java.util.Objects;

public class OrderedPizza extends RegisterEntry {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    char groeße;

    /**
     * Constructor where all values are empty or zero
     */
    public OrderedPizza() {
        this("", 0.0, ' ');

    }

    /**
     * @param name
     */
    public OrderedPizza(String name) {
        super(name);
    }

    /**
     * @param name
     * @param preis
     * @param groeße
     */
    public OrderedPizza(String name, Double preis, char groeße) {
        super(name, preis);
        this.groeße = groeße;
    }

    /**
     * @return the Price of the Pizza which is ordered
     */
    public Double getPreis() {
        return preis;
    }

    /**
     * @param preis
     */
    public void setPreis(Double preis) {
        this.preis = preis;
    }

    /**
     * @return the Size which this Pizza has
     */
    public char getGroeße() {
        return groeße;
    }

    public void setGroeße(char groeße) throws InvalidEntryException {
        Character.toLowerCase(groeße);
        if (groeße == 'k' || groeße == 'm' || groeße == 'g' || groeße == 'f') {
            this.groeße = groeße;
        } else {
            throw new InvalidEntryException("Eine Pizza kann nur klein, mittel, groß oder eine Familienpizza sein");
        }
    }

    /**
     * @return the Parameters as String Collection
     */
    @Override
    public String toString() {
        return super.toString();
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
     * @return the calculated hashCode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), groeße);
    }
}
