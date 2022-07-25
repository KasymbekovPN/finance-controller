package kpn.financecontroller.data.domains.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractDomain<Long>{
    private String name;

    public Country(CountryEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
    }

    @Override
    public String getInfo() {
        return getName();
    }
}