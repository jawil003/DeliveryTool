package Testing;

import Model.PizzenDB.PizzaIngredientConnection;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PizzaRelationTest {
    private static SessionFactory factory;

    @BeforeAll
    public static void prepare() {
        factory = new Configuration().configure().buildSessionFactory();

    }

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
