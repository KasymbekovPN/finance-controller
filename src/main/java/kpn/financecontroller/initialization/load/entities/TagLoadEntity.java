package kpn.financecontroller.initialization.load.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagLoadEntity extends AbstractLoadEntity<Long> {
    private String name;
}
