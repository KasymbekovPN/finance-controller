package kpn.financecontroller.data.domains.tag;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.tag.TagEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractDomain {
    private String name;

    public Tag(TagEntity entity) {
        id = entity.getId();
        name = entity.getName();
    }

    @Override
    public String getInfo() {
        return getName();
    }
}
