package Model.Kasse;

import java.util.Objects;

public class RegistryEntryWrapper {
    private OrderedPizza pizza;
    private int size;

    public RegistryEntryWrapper(OrderedPizza pizza, int size){
        this.pizza=pizza;
        this.size=size;
    }

    public OrderedPizza getPizza() {
        return pizza;
    }

    public void setPizza(OrderedPizza pizza) {
        this.pizza = pizza;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistryEntryWrapper that = (RegistryEntryWrapper) o;
        return Objects.equals(pizza, that.pizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizza);
    }
}
