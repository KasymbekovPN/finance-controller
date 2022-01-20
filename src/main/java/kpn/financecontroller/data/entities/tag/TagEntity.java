package kpn.financecontroller.data.entities.tag;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "tag")
public class TagEntity extends AbstractEntity {
    @NotEmpty
    @Size(max = 64)
    @Column(unique = true)
    private String name;

    public TagEntity(Tag tag) {
        id = tag.getId();
        name = tag.getName();
    }
}
