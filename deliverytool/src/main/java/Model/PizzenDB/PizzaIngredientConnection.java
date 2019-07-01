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
@NamedQueries(
        @NamedQuery(name = "PizzaIngredientConnection.getById", query = "from PizzaIngredientConnection where id.pizzaId=:id")
)

/**
 * @author Jannik Will
 * @version 1.0
 */

public class PizzaIngredientConnection implements Serializable {

    public final static String getById = "PizzaIngredientConnection.getById";

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
