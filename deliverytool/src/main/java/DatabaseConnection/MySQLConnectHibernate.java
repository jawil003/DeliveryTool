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
    private static MySQLConnectHibernate entity;

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private MySQLConnectHibernate() throws ServiceException {
        setup();
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
        closeSession();
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
            log.debug("Create new SessionFactory={}.", sessionFactory);
        }
    }

    public void setIngredientConnection(PizzaIngredientConnection connection) {
        createSessionIfNecessary();
        beginTransaction();
        session.save(connection);
        commitAndcloseSession();
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
        if (session == null) {
            session = sessionFactory.openSession();
        }
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
            log.debug("Open Session={}", session);
        }
    }

    public Pizza getPizza(Long id) {
        createSessionIfNecessary();
        beginTransaction();
        final Pizza pizza = session.get(Pizza.class, id);
        closeSession();
        return pizza;
    }

    private void beginTransaction() {
        session.beginTransaction();
        log.debug("Beginning the transaction");
    }


    private void create(Pizza p) {
        final Pizza entity = executeTransaction(p);
        session.save(entity);
        commitAndcloseSession();
        log.debug("Fired Pizza={} to database successful", p);
    }

    private void commitAndcloseSession() {
        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeSession();
        }
    }

    private void closeSession() {
        if (session.isOpen()) {
            session.close();
        }
    }

    private void create(Ingredient p) {
        createSessionIfNecessary();
        final Ingredient entity = executeTransaction(p);
        session.save(entity);
        commitAndcloseSession();
        log.debug("Fired Ingredience={} to database successful", p);

    }

    private void update(Pizza p) {
        createSessionIfNecessary();
        final Pizza entity = executeTransaction(p);
        session.update(entity);
        commitAndcloseSession();
        log.debug("Update Pizza={} on database successful", p);

    }

    private void delete(Pizza p) {
        createSessionIfNecessary();
        Pizza entity = executeTransaction(p);
        session.delete(entity);
        commitAndcloseSession();
        log.debug("Deleted Pizza={} on database successful", p);
    }

    private Pizza executeTransaction(Pizza p) {
        Pizza entity = new Pizza(p.getName(), p.getSmallPrice(), p.getMiddlePrice(), p.getBigPrice(), p.getFamilyPrice());
        log.debug("Convert Pizza={} to Pizza={}", p, entity);
        return entity;

    }

    private Ingredient executeTransaction(Ingredient e) {
        Ingredient entity = new Ingredient(e.getName());
        log.debug("Convert Ingredient={} to Ingredient={}", e, entity);
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
        createSessionIfNecessary();
        beginTransaction();
        delete(p);
        log.info("Delete Pizza={} from database ", p);
        commitAndcloseSession();
    }

    /**
     * Add a new Pizza Entry to the database by inherit INSERT Statement in background
     *
     * @param pizza
     */
    public void addPizza(Pizza pizza) {
        create(pizza);
        log.info("Create Pizza={} in database ", pizza);
    }

    @Override
    public void addIngredience(Ingredient e) {
        create(e);
        log.info("Create Ingredient={} in database ", e);
    }


    /**
     * Make a SQL SELECT Request to the mySQL DB and returns all Pizza Entries as a LinkedList.
     *
     * @return LinkedList of Pizza Entries
     */
    public List<Pizza> getPizzas() {
        createSessionIfNecessary();
        beginTransaction();
        final List<Pizza> list = session.createNamedQuery(Pizza.findAll, Pizza.class).list();
        closeSession();
        return list;

    }

    public List<Ingredient> getZutaten() {
        createSessionIfNecessary();
        beginTransaction();
        final List<Ingredient> list = session.createNamedQuery(Ingredient.findAll, Ingredient.class).list();
        closeSession();
        return list;
    }

    /**
     * @return true (if SQLConnection still runs), else false
     */
    public boolean isRunning() {
        return session.getTransaction().isActive();
    }
}
