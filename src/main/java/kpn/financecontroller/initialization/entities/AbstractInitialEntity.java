package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
abstract public class AbstractInitialEntity<K> {
    protected K id;
    protected String possibility;
}
