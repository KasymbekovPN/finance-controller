package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
final public class ProductJsonEntity extends AbstractJsonEntity{
    private String name;
    private Set<Long> tags;
}
