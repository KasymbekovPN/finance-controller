package kpn.financecontroller.data.domain.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Country {
    private Long id;
    private String name;

    public Country(CountryEntity entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
