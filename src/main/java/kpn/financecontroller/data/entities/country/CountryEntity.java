package kpn.financecontroller.data.entities.country;

import kpn.financecontroller.data.domains.country.Country;
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
@Entity(name = "country")
public class CountryEntity extends AbstractEntity {
    @NotEmpty
    @Column(unique = true)
    @Size(max = 100)
    private String name;

    public CountryEntity(Country country) {
        id = country.getId();
        name = country.getName();
    }
}
