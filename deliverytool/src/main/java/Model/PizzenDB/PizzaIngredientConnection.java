/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Pizza_Ingredience_Relation")
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
@NamedQueries({
        @NamedQuery(name = "PizzaIngredientConnection.getByPizzaId", query = "from PizzaIngredientConnection where id.pizzaId=:id"),
        @NamedQuery(name = "PizzaIngredientConnection.deleteByPizzaId", query = "delete from PizzaIngredientConnection where id.pizzaId =:id"),
        @NamedQuery(name = "PizzaIngredientConnection.deleteByIngredientId", query = "delete from PizzaIngredientConnection where id.ingredientId =:id"),
        @NamedQuery(name = "PizzaIngredientConnection.deleteAll", query = "delete from PizzaIngredientConnection ")
})

/**
 * @author Jannik Will
 * @version 1.0
 */

public class PizzaIngredientConnection implements Serializable {

    public final static String getByPizzaId = "PizzaIngredientConnection.getByPizzaId";
    public final static String deleteByPizzaId = "PizzaIngredientConnection.deleteByPizzaId";
    public final static String deleteByIngredientId = "PizzaIngredientConnection.deleteByIngredientId";
    public final static String deleteAll = "PizzaIngredientConnection.deleteAll";


    @EmbeddedId
    @Setter
    private PizzaIngredientID id;
    @ManyToOne
    @JoinColumn(name = "PizzaID", insertable = false, updatable = false)
    private Pizza pizza;
    @ManyToOne
    @JoinColumn(name = "IngredientID", insertable = false, updatable = false)
    private Ingredient ingredient;
    @Column(name = "Amount")
    @Setter
    private int amount;
    @Column(name = "Volume_Unit")
    @Setter
    private char unit;

    public PizzaIngredientConnection() {

    }

    public PizzaIngredientConnection(long pizzaId, long ingredientId) {
        id = new PizzaIngredientID(pizzaId, ingredientId);
    }
}
