package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
final public class PlaceJsonEntity extends AbstractJsonEntity{
    private String name;
    private Boolean online;
    private Long addressId;
}
