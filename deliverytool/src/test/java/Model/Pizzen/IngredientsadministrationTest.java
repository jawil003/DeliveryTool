/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Jannik Will
 * @version 1.0
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class IngredientsadministrationTest {
    @Mock
    private static Logger loggerMock;
    @Mock
    private static SessionFactory mockSessionFactory;
    private Ingredientsadministration ingredientsadministration = Ingredientsadministration.getInstance();

    @BeforeClass
    public static void prepare() {
        PowerMockito.mockStatic(LoggerFactory.class);
        when(LoggerFactory.getLogger(any(Class.class))).
                thenReturn(loggerMock);
        PowerMockito.mockStatic(SessionFactory.class);
        when(new Configuration().configure().buildSessionFactory()).
                thenReturn(mockSessionFactory);
    }

    @Test
    public void addZutaten() {
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Oregano"));
        ingredientsadministration.add(new Ingredient("Kümmel"));
        ingredientsadministration.add(new Ingredient("Tabasco"));
        ingredientsadministration.add(new Ingredient("Käse"));
        assertEquals(5, ingredientsadministration.getSize());
    }

    @Test
    public void addNull() {
        ingredientsadministration = Ingredientsadministration.getInstance();
    }

    @Test
    public void addZutatNull() {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.add(new Ingredient(null));
    }

    @Test
    public void addZutatenDuplicate() {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));
        ingredientsadministration.add(new Ingredient("Petersilie"));

        assertEquals(1, ingredientsadministration.getSize());
    }

    @Test
    public void deletePizzaNegative() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ingredientsadministration = Ingredientsadministration.getInstance();
        ingredientsadministration.delete(-1);
    }

    @Test
    public void addAndDeleteAllPizzenEachByEach() throws Exception {
        addZutaten();

        for (int i = ingredientsadministration.getSize() - 1; i >= 0; i--) {
            ingredientsadministration.delete(i);
        }
        assertEquals(0, ingredientsadministration.getSize());
    }

    @Test
    public void addAndDelete() throws Exception {
        addZutaten();
        ingredientsadministration.deleteAll();

        assertEquals(0, ingredientsadministration.getSize());

    }
}
