package kpn.financecontroller.initialization.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractEntity {
    private Long id;
}
