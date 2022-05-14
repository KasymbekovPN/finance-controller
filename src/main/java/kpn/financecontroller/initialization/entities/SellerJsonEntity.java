package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
final public class SellerJsonEntity extends AbstractJsonEntity{
    private String name;
    private String url;
    private String description;
    private Long addressId;
}
