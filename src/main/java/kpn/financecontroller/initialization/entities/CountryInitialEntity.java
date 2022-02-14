package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryInitialEntity extends AbstractInitialEntity<Long>{
    private String name;
}
