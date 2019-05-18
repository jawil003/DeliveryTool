package Model.Kasse;

public class BestelltePizza extends KassenEintrag {
    char groeße;

    public BestelltePizza() {
        this("", 0.0, ' ');

    }

    public BestelltePizza(String name) {
        super(name);
    }

    public BestelltePizza(String name, Double preis, char groeße) {
        super(name, preis);
        this.groeße = groeße;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

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

    @Override
    public String toString() {
        return "BestelltePizza{" +
                "preis=" + preis +
                '}';
    }
}
