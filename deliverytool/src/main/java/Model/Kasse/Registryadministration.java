/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.*;
import org.hibernate.criterion.Order;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Registryadministration {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private double gesamterPreis;

    private ObservableMap<OrderedPizza,Integer> kassenEintraege;

    /**
     * Contructor where the Registryadministration is initalized
     */
    public Registryadministration() {
        kassenEintraege = FXCollections.observableHashMap();
        addListener();
    }

    private void addListener() {
        kassenEintraege.addListener(new MapChangeListener<RegisterEntry, Integer>() {
            @Override
            public void onChanged(Change<? extends RegisterEntry, ? extends Integer> change) {

            }
        }
    }

    /**
     * @return the Obervablelist of KassenEintraege
     */
    public ObservableList<OrderedPizza> getKassenEintraege() {
        return FXCollections.observableArrayList(new ArrayList<OrderedPizza>(kassenEintraege.keySet()));
    }

    /**
     * Add a new RegisterEntry to the List
     *
     * @param kassenEintrag
     */
    public void addKassenEintrag(OrderedPizza kassenEintrag) {
        if(kassenEintraege.containsKey(kassenEintrag)){
            kassenEintraege.put(kassenEintrag, kassenEintraege.get(kassenEintraege)+1);
        }else{
            kassenEintraege.put(kassenEintrag, 1);
        }

    }

    public RegisterEntry removeKassenEintrag(int index) {
        final Iterator<OrderedPizza> iterator = kassenEintraege.keySet().iterator();
        OrderedPizza value = null;
        for(int i=0; i<index;i++){
            if(iterator.hasNext()){
                value = iterator.next();
            }else{
                return null;
            }

        }

        kassenEintraege.remove(value);
        return value;
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

    public String toEuroValue() {
        return String.format("%.2f", gesamterPreis) + "€";
    }

    public void createPDF(String path, double gesamterPreis) throws IOException, URISyntaxException {

        BonCreator<OrderedPizza> creator = new BonCreator<>(this, gesamterPreis, path);
        creator.addPizzas(FXCollections.observableArrayList(new ArrayList<OrderedPizza>(kassenEintraege.keySet())), gesamterPreis);
        creator.close();
    }
}
