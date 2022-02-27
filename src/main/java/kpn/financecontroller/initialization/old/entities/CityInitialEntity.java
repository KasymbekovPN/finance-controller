package kpn.financecontroller.initialization.old.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: 27.02.2022 del ???
@Setter
@Getter
@NoArgsConstructor
public class CityInitialEntity extends AbstractInitialEntity<Long>{
    private String name;
    private Long regionId;
}
