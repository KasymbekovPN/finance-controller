package kpn.financecontroller.data.entities.country;

import kpn.financecontroller.data.domains.country.Country;
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
@Entity(name = "countries")
public final class CountryEntity extends AbstractEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    @Size(max = 100)
    private String name;

    public CountryEntity(Country country) {
        setId(country.getId());
        setName(country.getName());
    }
}