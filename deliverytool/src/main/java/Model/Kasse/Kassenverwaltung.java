/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Kassenverwaltung {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private ObservableList<BestelltePizza> kassenEintraege;

    /**
     * Contructor where the Kassenverwaltung is initalized
     */
    public Kassenverwaltung() {
        kassenEintraege = FXCollections.observableArrayList();
    }

    /**
     * @return the Obervablelist of KassenEintraege
     */
    public ObservableList<BestelltePizza> getKassenEintraege() {
        return kassenEintraege;
    }

    /**
     * Add a new KassenEintrag to the List
     *
     * @param kassenEintrag
     */
    public void addKassenEintrag(BestelltePizza kassenEintrag) {
        this.kassenEintraege.add(kassenEintrag);
    }

    public KassenEintrag removeKassenEintrag(int index) {
        return kassenEintraege.remove(index);
    }

    /**
     * Remove a matching KassenEintrag
     *
     * @param kassenEintrag
     */
    public void removeKassenEintrag(BestelltePizza kassenEintrag) {
        kassenEintraege.remove(kassenEintrag);
    }

    /**
     * @return the Parameters as String
     */
    @Override
    public String toString() {
        return "Kassenverwaltung{" +
                "kassenEintraege=" + kassenEintraege +
                '}';
    }
}
