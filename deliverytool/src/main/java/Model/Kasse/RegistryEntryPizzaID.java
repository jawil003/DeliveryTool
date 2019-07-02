package Model.Kasse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class RegistryEntryPizzaID implements Serializable {
    @Column(name = "RegistryEntryID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long registryEntryId;
    @Column(name = "PizzaID")
    private long pizzaId;

    public RegistryEntryPizzaID(long pizzaId) {
        this.pizzaId = pizzaId;
    }
}
