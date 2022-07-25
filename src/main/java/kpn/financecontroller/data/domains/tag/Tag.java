package kpn.financecontroller.data.domains.tag;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractDomain<Long> {
    private String name;

    public Tag(TagEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
    }

    @Override
    public String getInfo() {
        return getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
