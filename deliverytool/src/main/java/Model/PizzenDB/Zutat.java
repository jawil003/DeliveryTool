package Model.PizzenDB;

import java.util.Objects;

public class Zutat {
    private String name;
    private int menge;
    private String mengeneinheit;

    public Zutat(String name, int menge, String mengeneinheit) {
        this.name = name;
        this.menge = menge;
        this.mengeneinheit = mengeneinheit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public String getMengeneinheit() {
        return mengeneinheit;
    }

    public void setMengeneinheit(String mengeneinheit) {
        this.mengeneinheit = mengeneinheit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zutat zutat = (Zutat) o;
        return menge == zutat.menge &&
                Objects.equals(name, zutat.name) &&
                Objects.equals(mengeneinheit, zutat.mengeneinheit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, menge, mengeneinheit);
    }
}
