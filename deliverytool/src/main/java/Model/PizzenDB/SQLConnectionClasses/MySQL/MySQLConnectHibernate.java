/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses.MySQL;

import Model.PizzenDB.Pizza;
import Model.PizzenDB.SQLConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
    public MySQLConnectHibernate() {
        setup();
        session = sessionFactory.openSession();
    }

    private void setup() {
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
        final MySQLPizzaHibernateEntity entity = executeTransaction(p);
        session.save(entity);
        session.getTransaction().commit();
    }

    private void update(Pizza p) {
        final MySQLPizzaHibernateEntity entity = executeTransaction(p);
        session.update(entity);
        session.getTransaction().commit();

    }

    private void delete(Pizza p) {
        MySQLPizzaHibernateEntity entity = executeTransaction(p);
        session.delete(entity);
        session.getTransaction().commit();
    }

    private MySQLPizzaHibernateEntity executeTransaction(Pizza p) {
        createSessionIfNecessary();
        beginTransaction();
        MySQLPizzaHibernateEntity entity = new MySQLPizzaHibernateEntity(p.getName(), p.getPreisKlein().orElse(0.00), p.getPreisMittel().orElse(0.00), p.getPreisGroß().orElse(0.00), p.getPreisFamilie().orElse(0.00));
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

    /**
     * Make a SQL SELECT Request to the mySQL DB and returns all Pizza Entries as a LinkedList.
     *
     * @return LinkedList of Pizza Entries
     */
    public List getPizzen() {
        final List<MySQLPizzaHibernateEntity> select_a_from_pizza_a = session.createQuery("SELECT a FROM Pizza a", MySQLPizzaHibernateEntity.class).getResultList();
        List<Pizza> pizzen = new LinkedList<>();
        for (MySQLPizzaHibernateEntity e : select_a_from_pizza_a) {
            pizzen.add(e.toPizza());
        }
        return pizzen;
    }

    /**
     * @return true (if SQLConnection still runs), else false
     */
    public boolean isRunning() {
        return session.getTransaction().isActive();
    }
}
