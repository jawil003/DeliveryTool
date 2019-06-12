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

    private ObservableSet<RegistryEntryWrapper> kassenEintraege;

    /**
     * Contructor where the Registryadministration is initalized
     */
    public Registryadministration() {
        kassenEintraege = FXCollections.observableSet();
        addListener();
    }

    private void addListener() {
        kassenEintraege.addListener(new SetChangeListener<RegistryEntryWrapper>() {
            @Override
            public void onChanged(Change<? extends RegistryEntryWrapper> change) {
                final RegistryEntryWrapper elementAdded = change.getElementAdded();
                if (elementAdded != null) {
                    gesamterPreis += elementAdded.getE().getPreis();
                }
                final RegistryEntryWrapper elementRemoved = change.getElementRemoved();
                if (elementRemoved != null) {
                    gesamterPreis += elementAdded.getE().getPreis();
                }
            }
        });
    }

    /**
     * @return the Obervablelist of KassenEintraege
     */
    public ObservableList<RegistryEntryWrapper> getKassenEintraege() {
        return FXCollections.observableArrayList(new ArrayList<RegistryEntryWrapper>(kassenEintraege));
    }

    /**
     * Add a new RegisterEntry to the List
     *
     * @param kassenEintrag
     */
    public void addKassenEintrag(OrderedPizza kassenEintrag) {
        final RegistryEntryWrapper registryEntryWrapper = new RegistryEntryWrapper(kassenEintrag, 1);
        if(kassenEintraege.contains(registryEntryWrapper)){
            registryEntryWrapper.setSize(registryEntryWrapper.getSize()+1);
        }else{
            kassenEintraege.add(registryEntryWrapper);
        }


    }

    public RegistryEntryWrapper removeKassenEintrag(int index) {
        final Iterator<RegistryEntryWrapper> iterator = kassenEintraege.iterator();
        RegistryEntryWrapper e = null;
        for(int i=0; i<=index&&i<kassenEintraege.size();i++){
           e=iterator.next();
        }
        kassenEintraege.remove(e);
        return e;
    }

    /**
     * Remove a matching RegisterEntry
     *
     * @param kassenEintrag
     */
    public void removeKassenEintrag(OrderedPizza kassenEintrag) {
        kassenEintraege.remove(kassenEintrag);
    }

    public double getGesamterPreis() {
        return gesamterPreis;
    }

    public void setGesamterPreis(double gesamterPreis) {
        this.gesamterPreis = gesamterPreis;
    }

    /**
     * @return the Parameters as String
     */
    @Override
    public String toString() {
        return "Registryadministration{" +
                "kassenEintraege=" + kassenEintraege+
                '}';
    }

    public String toEuroValue() {
        return String.format("%.2f", gesamterPreis) + "€";
    }

    public void createPDF(String path, double gesamterPreis) throws IOException, URISyntaxException {

        BonCreator creator = new BonCreator(this, gesamterPreis, path);
        creator.addPizzas(FXCollections.observableArrayList(new ArrayList<RegistryEntryWrapper>(kassenEintraege)), gesamterPreis);
        creator.close();
    }
}
