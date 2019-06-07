/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB.SQLConnectionClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Pizza")
public class MySQLPizzaHibernateEntity {
    private String id;
    private String name;
    private double smallPrice;
    private double middlePrice;
    private double bigPrice;
    private double familyPrice;

    public MySQLPizzaHibernateEntity(String name, double smallPrice, double middlePrice, double bigPrice, double familyPrice) {
        this.name = name;
        this.smallPrice = smallPrice;
        this.middlePrice = middlePrice;
        this.bigPrice = bigPrice;
        this.familyPrice = familyPrice;
    }

    public MySQLPizzaHibernateEntity() {
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PreisKlein")
    public double getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(double smallPrice) {
        this.smallPrice = smallPrice;
    }

    @Column(name = "PreisMittel")
    public double getMiddlePrice() {
        return middlePrice;
    }

    public void setMiddlePrice(double middlePrice) {
        this.middlePrice = middlePrice;
    }

    @Column(name = "PreisGroß")
    public double getBigPrice() {
        return bigPrice;
    }

    public void setBigPrice(double bigPrice) {
        this.bigPrice = bigPrice;
    }

    @Column(name = "PreisFamilie")
    public double getFamilyPrice() {
        return familyPrice;
    }

    public void setFamilyPrice(double familyPrice) {
        this.familyPrice = familyPrice;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
