package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.country.Country;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Region {
    private Long id;
    private String name;
    private Country country;
}
