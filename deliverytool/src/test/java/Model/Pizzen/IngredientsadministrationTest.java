/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import javafx.collections.FXCollections;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * @author Jannik Will
 * @version 1.0
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Ingredientsadministration.class)
public class IngredientsadministrationTest {
    @Mock
    private static Ingredientsadministration ingredientsadministration;

    @BeforeClass
    public static void prepare() throws Exception {
        ingredientsadministration = spy(Ingredientsadministration.getInstance());
        MemberModifier.field(ingredientsadministration.getClass(), "zutaten").set(ingredientsadministration, FXCollections.observableArrayList());
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
