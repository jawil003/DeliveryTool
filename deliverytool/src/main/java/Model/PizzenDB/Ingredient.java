/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Zutatenliste")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
@org.hibernate.annotations.NamedQueries(
        @org.hibernate.annotations.NamedQuery(name = "Ingredient.findAll", query = "SELECT c FROM Ingredient c")
)
public class Ingredient {

    public final static String findAll = "Ingredient.findAll";

    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;

    //Constructor(s):
    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient() {
        this("");
    }

}
