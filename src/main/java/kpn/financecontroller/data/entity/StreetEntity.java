package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.data.entity.CityEntity;
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
@Entity(name = "streets")
public final class StreetEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;

    public StreetEntity(Street street) {
        setId(street.getId());
        setName(street.getName());
        setCityEntity(new CityEntity(street.getCity()));
    }
}
