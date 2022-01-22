package kpn.financecontroller.data.domains.tag;

import kpn.financecontroller.data.entities.tag.TagEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tag {
    private Long id;
    private String name;

    public Tag(TagEntity entity) {
        id = entity.getId();
        name = entity.getName();
    }

    @Override
    public String toString() {
        return name;
    }
}
