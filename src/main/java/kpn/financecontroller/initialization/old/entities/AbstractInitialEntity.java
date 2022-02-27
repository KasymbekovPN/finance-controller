package kpn.financecontroller.initialization.old.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// TODO: 27.02.2022 del ???
@Setter
@Getter
@EqualsAndHashCode
abstract public class AbstractInitialEntity<K> {
    protected K id;
}
