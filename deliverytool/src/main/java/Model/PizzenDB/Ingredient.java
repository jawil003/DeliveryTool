/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import java.util.Objects;

/**
 * @author Jannik Will
 * @version 1.0
 */


public class Ingredient {

    private String name;

    /**
     * @param name
     */
    public Ingredient(String name) {
        this.name = name;

    }

    /**
     * @return the name of the Ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(name, ingredient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
