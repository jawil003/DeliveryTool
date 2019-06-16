/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Iterator;
import java.util.Set;

public class Registryadministration {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private double gesamterPreis;

    private ObservableMap<RegisterEntry, Integer> kassenEintraege;


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
                final RegisterEntry key = change.getKey();
                final Integer valueAdded = change.getValueAdded();
                final Integer valueRemoved = change.getValueRemoved();
                if(valueRemoved!=null)
                gesamterPreis-=key.getPreis();
                if(valueRemoved!=null)
                gesamterPreis+=key.getPreis();
            }
        });
    }

    public void addListener(MapChangeListener listener){
        kassenEintraege.addListener(listener);
    }
    
    public int getSize(RegisterEntry entry) throws NoSuchEntryException {
        final Integer integer = kassenEintraege.get(entry);

        if (integer==null){
            throw new NoSuchEntryException("This Entry does not exist in here");
        }else{
            return integer;
        }
    }

    /**
     * @return the Obervablelist of KassenEintraege
     */
    public ObservableList<RegisterEntry> getKassenEintraege() {
        ObservableList<RegisterEntry> l = FXCollections.observableArrayList();
        for (RegisterEntry e : kassenEintraege.keySet()) {
            l.add(e);
        }

        return l;
    }

    /**
     * @param entry The entry value which should be returned
     * @return The Value from the Set which contains entry
     * @throws NoSuchEntryException when Element isn't stored yet
     */
    public RegisterEntry get(OrderedPizza entry) throws NoSuchEntryException {
        final Iterator<RegisterEntry> iterator = kassenEintraege.keySet().iterator();
        RegisterEntry next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (next.equals(entry)) {
                return next;
            }

        }

        throw new NoSuchEntryException("This Entry does not exist in here");

    }

    /**
     * @param index The position of the RegisterEntry in the Set
     * @return The Element if it is there
     * @throws NoSuchEntryException If no such Element is in Set this Exception is thrown
     */
    public RegisterEntry get(int index) throws NoSuchEntryException {
        int i = 0;
        for (RegisterEntry e : kassenEintraege.keySet()) {
            if (i == index) {
                return e;
            }
            i++;
        }

        throw new NoSuchEntryException("This Entry does not exist in here");
    }

    /**
     * @param entry The Entry which should be in the Set
     * @return True if the Entry is in there, else false
     */
    public boolean contains(RegisterEntry entry) {

        return kassenEintraege.keySet().contains(entry);

    }

    public void remove(RegisterEntry entry) throws NoSuchEntryException {
        final boolean remove = kassenEintraege.keySet().remove(entry);

        if (!remove) {
            throw new NoSuchEntryException("This Entry does not exist in here");
        }
    }

    /**
     * Add a new RegisterEntry to the List
     * @param entry The RegisterEntry which should be added
     */
    public void addRegisterEntry(RegisterEntry entry) throws NoSuchEntryException {
        assert (entry!=null);
        final Set<RegisterEntry> registerEntries = kassenEintraege.keySet();
        
        if(kassenEintraege.containsKey(entry)){
            final Integer entrySize = kassenEintraege.get(entry);
            kassenEintraege.put(entry, entrySize+1);
        }else{
            kassenEintraege.put(entry, 1);
        }
    }

    public RegisterEntry remove(int index) {
        final Iterator<RegisterEntry> iterator = kassenEintraege.keySet().iterator();
        RegisterEntry e = null;
        for (int i = 0; i <= index && i < kassenEintraege.size(); i++) {
            e = iterator.next();
        }
        kassenEintraege.remove(e);
        return e;
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
                "kassenEintraege=" + kassenEintraege +
                '}';
    }

    public String toEuroValue() {
        return String.format("%.2f", gesamterPreis) + "€";
    }

    /*public void createPDF(String path, double gesamterPreis) throws IOException, URISyntaxException {

        BonCreator creator = new BonCreator(this, gesamterPreis, path);
        creator.addPizzas(FXCollections.observableArrayList(new ArrayList<RegistryEntryWrapper>(kassenEintraege.keySet())), gesamterPreis);
        creator.close();
    }*/
}