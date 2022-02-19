package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CityInitialEntity extends AbstractInitialEntity<Long>{
    private String name;
    private Long regionId;
}
