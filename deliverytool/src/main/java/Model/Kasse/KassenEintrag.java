/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import java.util.Objects;

public abstract class KassenEintrag {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    Double preis;
    private String name;

    /**
     * The Constructor where all values are empty or zero
     */
    public KassenEintrag() {
        this("", 0.0);
    }

    /**
     * @param name
     */
    public KassenEintrag(String name) {
        this.name = name;
    }

    /**
     * @param name
     * @param preis
     */
    public KassenEintrag(String name, Double preis) {
        this.name = name;
        this.preis = preis;
    }

    /**
     * @return the name of the added Entry
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Price of the added Entry
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
     * @return the Name and Price as String
     */
    @Override
    public String toString() {
        return name + ": " + preis;
    }

    /**
     * @param o
     * @return true (if the values of both compared KasseEintrag is matching), else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KassenEintrag that = (KassenEintrag) o;
        return Objects.equals(preis, that.preis) &&
                Objects.equals(name, that.name);
    }

    /**
     * @return the hashCode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(preis, name);
    }
}
