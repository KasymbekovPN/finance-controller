package kpn.financecontroller.data.initialEntities.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true) // TODO: 30.01.2022 del
public class TagIE extends AbstractIE {
    private String name;
}
