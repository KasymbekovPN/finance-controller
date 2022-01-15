package kpn.financecontroller.data.entities.street;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "street")
public class StreetEntity extends AbstractEntity {

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;

    public StreetEntity(Street street) {
        id = street.getId();
        name = street.getName();
        this.cityEntity = street.getCity() != null ? new CityEntity(street.getCity()) : null;
    }
}
