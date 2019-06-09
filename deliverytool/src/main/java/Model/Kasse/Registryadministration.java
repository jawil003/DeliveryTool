/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URISyntaxException;

public class Registryadministration {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private ObservableList<OrderedPizza> kassenEintraege;

    /**
     * Contructor where the Registryadministration is initalized
     */
    public Registryadministration() {
        kassenEintraege = FXCollections.observableArrayList();
    }

    /**
     * @return the Obervablelist of KassenEintraege
     */
    public ObservableList<OrderedPizza> getKassenEintraege() {
        return kassenEintraege;
    }

    /**
     * Add a new RegisterEntry to the List
     *
     * @param kassenEintrag
     */
    public void addKassenEintrag(OrderedPizza kassenEintrag) {
        this.kassenEintraege.add(kassenEintrag);
    }

    public RegisterEntry removeKassenEintrag(int index) {
        return kassenEintraege.remove(index);
    }

    /**
     * Remove a matching RegisterEntry
     *
     * @param kassenEintrag
     */
    public void removeKassenEintrag(OrderedPizza kassenEintrag) {
        kassenEintraege.remove(kassenEintrag);
    }

    /**
     * @return the Parameters as String
     */
    @Override
    public String toString() {
        return "Registryadministration{" +
                "kassenEintraege=" + kassenEintraege +
                '}';
    }

    public void createPDF(String path, double gesamterPreis) throws IOException, URISyntaxException {

        BonCreator<OrderedPizza> creator = new BonCreator<>(this, gesamterPreis, path);
        creator.addPizzas(kassenEintraege, gesamterPreis);
        creator.close();
    }
}
