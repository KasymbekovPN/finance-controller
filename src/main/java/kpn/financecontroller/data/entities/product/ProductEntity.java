package kpn.financecontroller.data.entities.product;

import kpn.financecontroller.data.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "product")
public class ProductEntity extends AbstractEntity {
    @NotEmpty
    @Column(unique = true)
    @Size(max = 256)
    private String name;
}
