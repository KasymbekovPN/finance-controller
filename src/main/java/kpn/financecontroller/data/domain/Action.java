package kpn.financecontroller.data.domain;

import kpn.lib.domain.AbstractDomain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Action extends AbstractDomain<Long> {
    private String description;
    private String algorithm;
    private boolean isValid;

    // TODO: 09.08.2022 add constructor

    @Override
    public String getInfo() {
        return getDescription();
    }
}
