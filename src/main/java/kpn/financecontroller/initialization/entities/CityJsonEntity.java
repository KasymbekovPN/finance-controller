package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
final public class CityJsonEntity extends AbstractJsonEntity{
    private String name;
    private Long regionId;
}
