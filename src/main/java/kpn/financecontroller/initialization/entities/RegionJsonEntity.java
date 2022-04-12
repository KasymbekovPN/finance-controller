package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionJsonEntity extends AbstractJsonEntity{
    private String name;
    private Long countryId;
}
