/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package DatabaseConnection;

import Model.Pizzen.Ingredient;
import Model.Pizzen.Pizza;
import Model.Pizzen.PizzaIngredientConnection;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jannik Will
 * @version 1.5
 */

@Slf4j
public class MySQLConnectHibernate implements SQLConnection {

    private static SessionFactory sessionFactory;
    private Session session;
    private Logger logger;
    private static MySQLConnectHibernate entity;

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private MySQLConnectHibernate() throws ServiceException {
        logger = LoggerFactory.getLogger(this.getClass());
        setup();
        session = sessionFactory.openSession();
    }

    public List<Ingredient> getIngredientConnectionsByPizzaId(long pizzaId) {
        createSessionIfNecessary();
        beginTransaction();
        final Query<PizzaIngredientConnection> namedQuery = session.createNamedQuery(PizzaIngredientConnection.getByPizzaId, PizzaIngredientConnection.class);
        namedQuery.setParameter("id", pizzaId);
        final List<PizzaIngredientConnection> list = namedQuery.list();
        LinkedList<Ingredient> ingredience = new LinkedList<>();
        for (PizzaIngredientConnection p : list) {
            ingredience.add(p.getIngredient());
        }

        return ingredience;
    }

    public static MySQLConnectHibernate getInstance() {
        if (entity == null) {
            entity = new MySQLConnectHibernate();
        }
        return entity;
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

    public Pizza getPizza(Long id) {
        createSessionIfNecessary();
        beginTransaction();
        final Pizza pizza = session.get(Pizza.class, id);
        session.close();
        return pizza;
    }

    private void beginTransaction() {
        session.beginTransaction();
        logger.debug("Beginning the transaction");
    }


    private void create(Pizza p) {
        final Pizza entity = executeTransaction(p);
        session.save(entity);
        commitAndcloseSession();
        logger.debug("Fired Pizza={} to database successful", p);
    }

    private void commitAndcloseSession() {
        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private void create(Ingredient p) {
        final Ingredient entity = executeTransaction(p);
        session.save(entity);
        commitAndcloseSession();
        logger.debug("Fired Ingredience={} to database successful", p);

    }

    private void update(Pizza p) {
        final Pizza entity = executeTransaction(p);
        session.update(entity);
        commitAndcloseSession();
        logger.debug("Update Pizza={} on database successful", p);

    }

    private void delete(Pizza p) {
        Pizza entity = executeTransaction(p);
        session.delete(entity);
        commitAndcloseSession();
        logger.debug("Deleted Pizza={} on database successful", p);
    }

    private Pizza executeTransaction(Pizza p) {
        createSessionIfNecessary();
        beginTransaction();
        Pizza entity = new Pizza(p.getName(), p.getSmallPrice(), p.getMiddlePrice(), p.getBigPrice(), p.getFamilyPrice());
        logger.debug("Convert Pizza={} to Pizza={}", p, entity);
        return entity;

    }

    private Ingredient executeTransaction(Ingredient e) {
        beginTransaction();
        Ingredient entity = new Ingredient(e.getName());
        logger.debug("Convert Ingredient={} to Ingredient={}", e, entity);
        return entity;
    }

    public void close(WindowEvent event) {
        assert (event.equals(WindowEvent.WINDOW_CLOSE_REQUEST));
        assert (sessionFactory != null);
        sessionFactory.close();
        sessionFactory = null;
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
        return session.createNamedQuery(Pizza.findAll, Pizza.class).list();
    }

    public List<Ingredient> getZutaten() {
        return session.createNamedQuery(Ingredient.findAll, Ingredient.class).list();
    }

    /**
     * @return true (if SQLConnection still runs), else false
     */
    public boolean isRunning() {
        return session.getTransaction().isActive();
    }
}
