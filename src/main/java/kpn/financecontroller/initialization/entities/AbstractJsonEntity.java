package kpn.financecontroller.initialization.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractJsonEntity {
    private Long id;
}
