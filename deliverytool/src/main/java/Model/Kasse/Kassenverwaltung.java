package Model.Kasse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Kassenverwaltung {

    private ObservableList<BestelltePizza> kassenEintraege;

    public Kassenverwaltung() {
        kassenEintraege = FXCollections.observableArrayList();
    }

    public ObservableList<BestelltePizza> getKassenEintraege() {
        return kassenEintraege;
    }

    public void addKassenEintrag(BestelltePizza kassenEintrag) {
        this.kassenEintraege.add(kassenEintrag);
    }

    public KassenEintrag removeKassenEintrag(int index) {
        return kassenEintraege.remove(index);
    }

    public void removeKassenEintrag(BestelltePizza kassenEintrag) {
        kassenEintraege.remove(kassenEintrag);
    }

    @Override
    public String toString() {
        return "Kassenverwaltung{" +
                "kassenEintraege=" + kassenEintraege +
                '}';
    }
}
