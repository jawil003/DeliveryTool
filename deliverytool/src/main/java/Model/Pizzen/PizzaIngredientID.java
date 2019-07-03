/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Model.Pizzen;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode

/**
 * @author Jannik Will
 * @version 1.0
 */

public class PizzaIngredientID implements Serializable {
    @Column(name = "PizzaID")
    private long pizzaId;
    @Column(name = "IngredientID")
    private long ingredientId;

    public PizzaIngredientID() {

    }

    public PizzaIngredientID(long pizzaId, long ingredientId) {
        this.pizzaId = pizzaId;
        this.ingredientId = ingredientId;
    }
}
