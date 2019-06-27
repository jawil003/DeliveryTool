/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses.MySQL;

import Model.PizzenDB.Ingredient;
import Model.PizzenDB.Pizza;
import Model.PizzenDB.SQLConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * SQL DB Connection
 *
 * @author Jannik Will
 */
public class MySQLConnectHibernate implements SQLConnection {

    private static SessionFactory sessionFactory;
    private Session session;

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public MySQLConnectHibernate() throws ServiceException {
        setup();
        session = sessionFactory.openSession();
    }

    private void setup() throws ServiceException {
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Set up the connection to the mySQL Database.
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private void createSessionIfNecessary() {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
    }

    private void beginTransaction() {
        session.beginTransaction();
    }


    private void create(Pizza p) {
        final MySQLPizzaHibernateEntityPizza entity = executeTransaction(p);
        session.save(entity);
        session.getTransaction().commit();
    }

    private void create(Ingredient p) {
        final MySQLPizzaHibernateEntityZutat entity = executeTransaction(p);
        session.save(entity);
        session.getTransaction().commit();
    }

    private void update(Pizza p) {
        final MySQLPizzaHibernateEntityPizza entity = executeTransaction(p);
        session.update(entity);
        session.getTransaction().commit();

    }

    private void delete(Pizza p) {
        MySQLPizzaHibernateEntityPizza entity = executeTransaction(p);
        session.delete(entity);
        session.getTransaction().commit();
    }

    private MySQLPizzaHibernateEntityPizza executeTransaction(Pizza p) {
        createSessionIfNecessary();
        beginTransaction();
        MySQLPizzaHibernateEntityPizza entity = new MySQLPizzaHibernateEntityPizza(p.getName(), p.getPreisKlein().orElse(0.00), p.getPreisMittel().orElse(0.00), p.getPreisGroß().orElse(0.00), p.getPreisFamilie().orElse(0.00));
        return entity;
    }

    private MySQLPizzaHibernateEntityZutat executeTransaction(Ingredient e) {
        beginTransaction();
        MySQLPizzaHibernateEntityZutat entity = new MySQLPizzaHibernateEntityZutat(e.getName());
        return entity;
    }

    /**
     * @throws SQLException
     */
    private void close() throws SQLException {
        if (session.isOpen()) {
            session.close();
        }
    }

    private void commitTransaction() {
        session.getTransaction().commit();
    }

    private void rollBackTransaction() {
        session.getTransaction().rollback();
    }


    /**
     * Delete the Pizza Entry drom Database
     *
     * @param p Pizza that should be deleted
     */
    public void deletePizza(Pizza p) {
        delete(p);
    }

    /**
     * Add a new Pizza Entry to the database by inherit INSERT Statement in background
     *
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        create(pizza);
    }

    @Override
    public void addIngredience(Ingredient e) {
        create(e);
    }


    /**
     * Make a SQL SELECT Request to the mySQL DB and returns all Pizza Entries as a LinkedList.
     *
     * @return LinkedList of Pizza Entries
     */
    public List<Pizza> getPizzas() {
        final List<MySQLPizzaHibernateEntityPizza> select_a_from_pizza_a = session.createQuery("SELECT a FROM Pizza a", MySQLPizzaHibernateEntityPizza.class).getResultList();
        List<Pizza> pizzen = new LinkedList<>();
        for (MySQLPizzaHibernateEntityPizza e : select_a_from_pizza_a) {
            pizzen.add(e.toPizza());
        }
        return pizzen;
    }

    public List<Ingredient> getZutaten() {
        final List<MySQLPizzaHibernateEntityZutat> select_a_from_zutat_a = session.createQuery("SELECT a FROM Zutatenliste a", MySQLPizzaHibernateEntityZutat.class).getResultList();
        List<Ingredient> zutaten = new LinkedList<>();
        for (MySQLPizzaHibernateEntityZutat e : select_a_from_zutat_a) {
            zutaten.add(e.toZutat());
        }
        return zutaten;
    }

    /**
     * @return true (if SQLConnection still runs), else false
     */
    public boolean isRunning() {
        return session.getTransaction().isActive();
    }
}
