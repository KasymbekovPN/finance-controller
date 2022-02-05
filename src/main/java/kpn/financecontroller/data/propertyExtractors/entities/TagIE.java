package kpn.financecontroller.data.propertyExtractors.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagIE extends AbstractIE<Long> {
    private String name;
}
