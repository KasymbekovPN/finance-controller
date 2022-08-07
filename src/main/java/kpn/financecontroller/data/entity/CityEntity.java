package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.domain.City;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "cities")
public final class CityEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity regionEntity;

    public CityEntity(City city) {
        setId(city.getId());
        setName(city.getName());
        setRegionEntity(new RegionEntity(city.getRegion()));
    }
}
