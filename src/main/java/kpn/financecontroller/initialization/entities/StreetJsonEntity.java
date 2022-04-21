package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
final public class StreetJsonEntity extends AbstractJsonEntity{
    private String name;
    private Long cityId;
}
