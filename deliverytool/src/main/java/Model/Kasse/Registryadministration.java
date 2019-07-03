/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Kasse;

import Collections.ObservableLinkedHashMultiSet;
import DatabaseConnection.MySQLConnectHibernate;
import DatabaseConnection.SQLConnection;
import javafx.collections.SetChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Jannik Will
 * @version 1.7
 */

@Slf4j
public class Registryadministration {

    private double gesamterPreis;

    private static Registryadministration registryadministration;

    private Logger logger;

    private SQLConnection connection;
    private ObservableLinkedHashMultiSet<OrderedPizza> kassenEintraege;


    /**
     * Contructor where the Registryadministration is initalized
     */
    private Registryadministration() {
        kassenEintraege = new ObservableLinkedHashMultiSet<>();
        logger = LoggerFactory.getLogger(this.getClass());
        connection = MySQLConnectHibernate.getInstance();
        addListener();
    }

    public static Registryadministration getInstance() {
        if (registryadministration == null) {
            registryadministration = new Registryadministration();
        }

        return registryadministration;
    }

    private void addListener() {
        kassenEintraege.addListener(change -> {
            if (change.wasRemoved()) {
                gesamterPreis -= change.getElementRemoved().getPreis();
            } else if (change.wasAdded()) {
                gesamterPreis += change.getElementAdded().getPreis();
            }
        });
    }

    public int getSize(OrderedPizza entry) {
        return kassenEintraege.count(entry);
    }

    /**
     * @return the Obervablelist of KassenEintraege
     */
    public Set<OrderedPizza> getKassenEintraege() {
        return kassenEintraege.elementSet();
    }

    /**
     * @param entry The entry value which should be returned
     * @return The Value from the Set which contains entry
     * @throws NoSuchEntryException when Element isn't stored yet
     */
    public OrderedPizza get(OrderedPizza entry) throws NoSuchEntryException {
        for (OrderedPizza e : kassenEintraege.elementSet()) {
            if (e.equals(entry)) {
                return e;
            }
        }

        throw new NoSuchEntryException("Element does not exist in here");

    }

    public void clear() {
        kassenEintraege.clear();
    }

    /**
     * @param index The position of the OrderedPizza in the Set
     * @return The Element if it is there
     * @throws NoSuchEntryException If no such Element is in Set this Exception is thrown
     */
    public OrderedPizza get(int index) throws NoSuchEntryException {
        int i = 0;
        for (OrderedPizza e : kassenEintraege.elementSet()) {
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
    public boolean contains(OrderedPizza entry) {

        return kassenEintraege.elementSet().contains(entry);

    }

    public void remove(OrderedPizza entry) throws NoSuchEntryException {
        kassenEintraege.remove(entry);
        logger.info("Removed Pizza={} with Price={} and Size={}", entry.getName(), entry.getPreis(), entry.getGroeße());

    }

    /**
     * Add a new OrderedPizza to the List
     * @param entry The OrderedPizza which should be added
     */
    /**
     * @todo Kasseneintraege to database
     * @body Create DB Connection to add Kasseneintraege to database
     */
    public void add(OrderedPizza entry) throws NoSuchEntryException {
        assert (entry!=null);
        kassenEintraege.add(entry);
        logger.info("Added Pizza={} with Price={} and Size={}", entry.getName(), entry.getPreis(), entry.getGroeße());

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

    public void addListener(SetChangeListener<OrderedPizza> setChangeListener) {
        kassenEintraege.addListener(setChangeListener);
    }

    /*public void createPDF(String path, double gesamterPreis) throws IOException, URISyntaxException {

        BonCreator creator = new BonCreator(this, gesamterPreis, path);
        creator.addPizzas(FXCollections.observableArrayList(new ArrayList<RegistryEntryWrapper>(kassenEintraege.keySet())), gesamterPreis);
        creator.close();
    }*/
}