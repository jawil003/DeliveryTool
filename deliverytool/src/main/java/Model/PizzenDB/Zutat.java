/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

public class Zutat {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private String name;
    private int menge;
    private String mengeneinheit;

    /**
     * @param name
     * @param menge
     * @param mengeneinheit
     */
    public Zutat(String name, int menge, String mengeneinheit) {
        this.name = name;
        this.menge = menge;
        this.mengeneinheit = mengeneinheit;
    }

    /**
     * @return the name of the Zutat
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
     * @return the amount of the Zutat
     */
    public int getMenge() {
        return menge;
    }

    /**
     * @param menge
     */
    public void setMenge(int menge) {
        this.menge = menge;
    }

    /**
     * @return the unit of the Zutat
     */
    public String getMengeneinheit() {
        return mengeneinheit;
    }

    /**
     * @param mengeneinheit
     */
    public void setMengeneinheit(String mengeneinheit) {
        this.mengeneinheit = mengeneinheit;
    }

    /**
     * @param o
     * @return true (if the values of both compared KasseEintrag is matching), else false
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zutat zutat = (Zutat) o;
        return menge == zutat.menge &&
                Objects.equals(name, zutat.name) &&
                Objects.equals(mengeneinheit, zutat.mengeneinheit);
    }

    /**
     * @return the hashCode Value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, menge, mengeneinheit);
    }
}
