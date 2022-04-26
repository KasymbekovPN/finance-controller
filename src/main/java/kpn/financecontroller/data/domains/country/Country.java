package kpn.financecontroller.data.domains.country;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.country.CountryEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractDomain {
    private String name;

    public Country(CountryEntity entity) {
        id = entity.getId();
        name = entity.getName();
    }

    @Override
    public String getInfo() {
        return getName();
    }
}
