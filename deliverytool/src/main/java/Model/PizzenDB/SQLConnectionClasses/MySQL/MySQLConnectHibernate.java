/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses.MySQL;

import Model.PizzenDB.Ingredient;
import Model.PizzenDB.Pizza;
import Model.PizzenDB.SQLConnection;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * SQL DB Connection
 *
 * @author Jannik Will
 */
@Slf4j
public class MySQLConnectHibernate implements SQLConnection {

    private static SessionFactory sessionFactory;
    private Session session;
    private Logger logger;

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public MySQLConnectHibernate() throws ServiceException {
        logger = LoggerFactory.getLogger(this.getClass());
        setup();
        session = sessionFactory.openSession();
    }

    private void setup() throws ServiceException {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            logger.debug("Create new SessionFactory={}.", sessionFactory);
        }
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
            logger.debug("Open Session={}", session);
        }
    }

    private void beginTransaction() {
        session.beginTransaction();
        logger.debug("Beginning the transaction");
    }


    private void create(Pizza p) {
        final MySQLPizzaHibernateEntity entity = executeTransaction(p);
        session.save(entity);
        session.getTransaction().commit();
        logger.debug("Fired Pizza={} to database successful", p);
    }

    private void create(Ingredient p) {
        final MySQLIngredientHibernateEntity entity = executeTransaction(p);
        session.save(entity);
        session.getTransaction().commit();
        logger.debug("Fired Ingredience={} to database successful", p);

    }

    private void update(Pizza p) {
        final MySQLPizzaHibernateEntity entity = executeTransaction(p);
        session.update(entity);
        session.getTransaction().commit();
        logger.debug("Update Pizza={} on database successful", p);

    }

    private void delete(Pizza p) {
        MySQLPizzaHibernateEntity entity = executeTransaction(p);
        session.delete(entity);
        session.getTransaction().commit();
        logger.debug("Deleted Pizza={} on database successful", p);
    }

    private MySQLPizzaHibernateEntity executeTransaction(Pizza p) {
        createSessionIfNecessary();
        beginTransaction();
        MySQLPizzaHibernateEntity entity = new MySQLPizzaHibernateEntity(p.getName(), p.getPreisKlein().orElse(0.00), p.getPreisMittel().orElse(0.00), p.getPreisGroß().orElse(0.00), p.getPreisFamilie().orElse(0.00));
        logger.debug("Convert Pizza={} to MySQLPizzaHibernateEntity={}", p, entity);
        return entity;

    }

    private MySQLIngredientHibernateEntity executeTransaction(Ingredient e) {
        beginTransaction();
        MySQLIngredientHibernateEntity entity = new MySQLIngredientHibernateEntity(e.getName());
        logger.debug("Convert Ingredient={} to MySQLIngredientHibernateEntity={}", e, entity);
        return entity;
    }

    /**
     * @throws SQLException
     */
    private void close() throws SQLException {
        if (session.isOpen()) {
            session.close();
            logger.debug("Closed Session={}", session);
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
        logger.info("Delete Pizza={} from database ", p);
    }

    /**
     * Add a new Pizza Entry to the database by inherit INSERT Statement in background
     *
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        create(pizza);
        logger.info("Create Pizza={} in database ", pizza);
    }

    @Override
    public void addIngredience(Ingredient e) {
        create(e);
        logger.info("Create Ingredient={} in database ", e);
    }


    /**
     * Make a SQL SELECT Request to the mySQL DB and returns all Pizza Entries as a LinkedList.
     *
     * @return LinkedList of Pizza Entries
     */
    public List<Pizza> getPizzas() {
        final List<MySQLPizzaHibernateEntity> select_a_from_pizza_a = session.createQuery("SELECT a FROM Pizza a", MySQLPizzaHibernateEntity.class).getResultList();
        List<Pizza> pizzen = new LinkedList<>();
        for (MySQLPizzaHibernateEntity e : select_a_from_pizza_a) {
            pizzen.add(e.toPizza());
            logger.debug("Getting EntityPizza={}", e);
        }
        logger.info("Getted List={} from database", pizzen);
        return pizzen;
    }

    public List<Ingredient> getZutaten() {
        final List<MySQLIngredientHibernateEntity> select_a_from_zutat_a = session.createQuery("SELECT a FROM Zutatenliste a", MySQLIngredientHibernateEntity.class).getResultList();
        List<Ingredient> zutaten = new LinkedList<>();
        for (MySQLIngredientHibernateEntity e : select_a_from_zutat_a) {
            zutaten.add(e.toZutat());
            logger.debug("Getting EntityIngrendient={}", e);
        }
        logger.info("Getted List={} from database", zutaten);
        return zutaten;
    }

    /**
     * @return true (if SQLConnection still runs), else false
     */
    public boolean isRunning() {
        return session.getTransaction().isActive();
    }
}
