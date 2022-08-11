package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.entity.ActionEntity;
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

    public Action(ActionEntity entity) {
        setId(entity.getId());
        setDescription(entity.getDescription());
        setAlgorithm(entity.getAlgorithm());
    }

    @Override
    public String getInfo() {
        return getDescription();
    }
}
