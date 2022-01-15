package kpn.financecontroller.data.entities.city;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
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
@Entity(name = "city")
public class CityEntity extends AbstractEntity {

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity regionEntity;

    public CityEntity(City city) {
        id = city.getId();
        name = city.getName();
        regionEntity = new RegionEntity(city.getRegion());
    }
}
