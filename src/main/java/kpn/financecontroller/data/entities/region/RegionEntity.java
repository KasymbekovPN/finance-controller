package kpn.financecontroller.data.entities.region;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "region")
public class RegionEntity extends AbstractEntity {

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    public RegionEntity(Region region) {
        id = region.getId();
        name = region.getName();
        countryEntity = new CountryEntity(region.getCountry());
    }
}
