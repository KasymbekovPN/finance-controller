package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
final public class AddressJsonEntity extends AbstractJsonEntity{
    private String name;
    private Long streetId;
}
