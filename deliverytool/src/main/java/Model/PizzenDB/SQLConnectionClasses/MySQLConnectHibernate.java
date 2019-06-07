/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses;

import Model.PizzenDB.Pizza;
import Model.PizzenDB.SQLConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * SQL DB Connection
 *
 * @author Jannik Will
 */
public class MySQLConnectHibernate implements SQLConnection {

    private SessionFactory sessionFactory;
    private Session session;

    private boolean isRunning;

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public MySQLConnectHibernate()
            throws Exception {
        isRunning = true;
        setup();
        establishConnection();
    }


    private void setup() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = com.hib.init.HibernateUtil.getSessionFactory();
    }

    private void create(Pizza p) {
        session.beginTransaction();
        MySQLPizzaHibernateEntity entity = new MySQLPizzaHibernateEntity(p.getName(), p.getPreisKlein().orElse(0.00), p.getPreisMittel().orElse(0.00), p.getPreisGroß().orElse(0.00), p.getPreisFamilie().orElse(0.00));
        session.save(entity);
        session.getTransaction().commit();
        session.getTransaction().commit();
        session.close();
    }

    private void update(Pizza p) {
        session.beginTransaction();
        MySQLPizzaHibernateEntity entity = new MySQLPizzaHibernateEntity(p.getName(), p.getPreisKlein().orElse(0.00), p.getPreisMittel().orElse(0.00), p.getPreisGroß().orElse(0.00), p.getPreisFamilie().orElse(0.00));
        session.update(entity);
        session.getTransaction().commit();
        session.close();

    }

    private void delete(Pizza p) {
        session.beginTransaction();
        MySQLPizzaHibernateEntity entity = new MySQLPizzaHibernateEntity(p.getName(), p.getPreisKlein().orElse(0.00), p.getPreisMittel().orElse(0.00), p.getPreisGroß().orElse(0.00), p.getPreisFamilie().orElse(0.00));
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    private Pizza getById(long id) {
        session.beginTransaction();
        final MySQLPizzaHibernateEntity entity = session.get(MySQLPizzaHibernateEntity.class, id);
        session.close();
        return new Pizza(entity.getName(), null, entity.getSmallPrice(), entity.getMiddlePrice(), entity.getBigPrice(), entity.getFamilyPrice());
    }

    /**
     * @throws SQLException
     */
    private void closeConnection() throws SQLException {
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        isRunning = false;
    }

    /**
     * Set up the connection to the mySQL Database.
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private void establishConnection()
            throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    /**
     * Make a SQL SELECT Request to the mySQL DB and returns all Pizza Entries as a LinkedList.
     *
     * @return LinkedList of Pizza Entries
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public List getPizzen()
            throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        return session.createQuery("SELECT a FROM MySQLPizzaHibernateEntity a", MySQLPizzaHibernateEntity.class).getResultList();
    }

    /**
     * Delete the Pizza Entry drom Database
     *
     * @param p Pizza that should be deleted
     * @throws SQLException
     */
    public void deletePizza(Pizza p) throws SQLException {
        delete(p);
    }

    /**
     * Add a new Pizza Entry to the database by inherit INSERT Statement in background
     *
     * @param pizza
     * @throws SQLException
     */
    public void addPizza(Pizza pizza) throws SQLException {
        create(pizza);
    }

    /**
     * @return true (if SQLConnection still runs), else false
     */
    public boolean isRunning() {
        return isRunning;
    }
}
