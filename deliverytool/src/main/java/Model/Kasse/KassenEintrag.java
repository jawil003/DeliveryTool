package Model.Kasse;

import java.util.Objects;

public abstract class KassenEintrag {
    Double preis;
    private String name;

    public KassenEintrag() {
        this("", 0.0);
    }

    public KassenEintrag(String name) {
        this.name = name;
    }

    public KassenEintrag(String name, Double preis) {
        this.name = name;
        this.preis = preis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    @Override
    public String toString() {
        return "KassenEintrag{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KassenEintrag that = (KassenEintrag) o;
        return Objects.equals(preis, that.preis) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preis, name);
    }
}
