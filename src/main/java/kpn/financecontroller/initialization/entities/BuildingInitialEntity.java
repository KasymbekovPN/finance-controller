package kpn.financecontroller.initialization.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuildingInitialEntity extends AbstractInitialEntity<Long>{
    private String name;
    private Long streetId;
}
