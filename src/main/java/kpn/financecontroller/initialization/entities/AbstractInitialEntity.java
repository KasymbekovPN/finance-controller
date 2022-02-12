package kpn.financecontroller.initialization.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
abstract public class AbstractInitialEntity<K> {
    protected K id;
}
