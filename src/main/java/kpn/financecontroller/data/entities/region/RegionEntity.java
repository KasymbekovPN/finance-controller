package kpn.financecontroller.data.entities.region;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.domains.region.Region;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "regions")
public final class RegionEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    public RegionEntity(Region region) {
        setId(region.getId());
        setName(region.getName());
        setCountryEntity(new CountryEntity(region.getCountry()));
    }
}
