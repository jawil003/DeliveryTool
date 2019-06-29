package Model.PizzenDB.SQLConnectionClasses.MySQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Pizza_Ingredience_Relation")
public class MySQLPizza_Ingredience_Relation_Entity {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "PizzaID")
    private int pizzaId;
    @Column(name = "IngredientID")
    private int ingredienceId;
    @Column(name = "Amount")
    private double amount;
    @Column(name = "Volume_Unit")
    private char volume_Unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getIngredienceId() {
        return ingredienceId;
    }

    public void setIngredienceId(int ingredienceId) {
        this.ingredienceId = ingredienceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public char getVolume_Unit() {
        return volume_Unit;
    }

    public void setVolume_Unit(char volume_Unit) {
        this.volume_Unit = volume_Unit;
    }
}
