package Testing;

import Model.PizzenDB.PizzaIngredientConnection;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class PizzaRelationTest {
    private static SessionFactory factory;

    @BeforeAll
    public static void prepare() {
        factory = new Configuration().configure().buildSessionFactory();

    }

    //WARNING: Just an test for Relation not an actual JUnitTest
    @Test
    @SneakyThrows
    public void addPizzaIngredience() {
        final Session session = factory.openSession();
        session.beginTransaction();
        PizzaIngredientConnection pizzaIngredientConnection = new PizzaIngredientConnection(1, 1);
        session.save(pizzaIngredientConnection);
        session.getTransaction().commit();
    }
}
