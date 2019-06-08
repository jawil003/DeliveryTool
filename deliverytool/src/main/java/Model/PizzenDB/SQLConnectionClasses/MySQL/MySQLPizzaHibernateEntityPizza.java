/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses.MySQL;

import Model.PizzenDB.Pizza;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Pizza")
public class MySQLPizzaHibernateEntityPizza {
    @Id
    @Column(name = "Name")
    private String name;
    @Column(name = "PreisKlein")
    private double smallPrice;
    @Column(name = "PreisMittel")
    private double middlePrice;
    @Column(name = "PreisGroß")
    private double bigPrice;
    @Column(name = "PreisFamilie")
    private double familyPrice;

    public MySQLPizzaHibernateEntityPizza(String name, double smallPrice, double middlePrice, double bigPrice, double familyPrice) {
        this.name = name;
        this.smallPrice = smallPrice;
        this.middlePrice = middlePrice;
        this.bigPrice = bigPrice;
        this.familyPrice = familyPrice;
    }

    public MySQLPizzaHibernateEntityPizza() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(double smallPrice) {
        this.smallPrice = smallPrice;
    }

    public double getMiddlePrice() {
        return middlePrice;
    }

    public void setMiddlePrice(double middlePrice) {
        this.middlePrice = middlePrice;
    }

    public double getBigPrice() {
        return bigPrice;
    }

    public void setBigPrice(double bigPrice) {
        this.bigPrice = bigPrice;
    }

    public double getFamilyPrice() {
        return familyPrice;
    }

    public void setFamilyPrice(double familyPrice) {
        this.familyPrice = familyPrice;
    }

    public Pizza toPizza() {
        return new Pizza(name, null, smallPrice, middlePrice, bigPrice, familyPrice);
    }
}
