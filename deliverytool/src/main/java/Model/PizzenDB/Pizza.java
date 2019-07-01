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
@Table(name = "Pizza")
@Getter
@Setter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
@NamedQueries(
        @NamedQuery(name = "Pizza.findAll", query = "SELECT a FROM Pizza a")
)
public class Pizza implements Serializable {
    public final static String findAll = "Pizza.findAll";
    @Id
    @Column(name = "ID")
    private int id;
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

    //Constructor(s):
    public Pizza(String name, double smallPrice, double middlePrice, double bigPrice, double familyPrice) {
        this.name = name;
        this.smallPrice = smallPrice;
        this.middlePrice = middlePrice;
        this.bigPrice = bigPrice;
        this.familyPrice = familyPrice;
    }

    public Pizza() {

    }

    public Pizza(String name) {
        this(name, 0.0, 0.0, 0.0, 0.0);
    }

    //Nothing here because lombok does the magic
}
