package kpn.financecontroller.data.entities.currency;

import kpn.financecontroller.data.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity(name = "currency")
public class CurrencyEntity extends AbstractEntity {
    @NotEmpty
    @Column(unique = true)
    @Size(min = 3, max = 3)
    private String code;
}
