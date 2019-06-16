/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses.MySQL;

import Model.PizzenDB.Ingredient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "Zutatenliste")
public class MySQLPizzaHibernateEntityZutat {

    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Name")
    private String name;

    public MySQLPizzaHibernateEntityZutat(String name) {
        this.name = name;
    }

    public MySQLPizzaHibernateEntityZutat() {
        this("");
    }


    public Ingredient toZutat() {
        return new Ingredient(name);
    }
}
