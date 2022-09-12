package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.domain.Action;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "actions")
public final class ActionEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    @NotEmpty
    @Type(type="org.hibernate.type.TextType")
    private String algorithm;

    public ActionEntity(Action action) {
        setId(action.getId());
        setDescription(action.getDescription());
        setAlgorithm(action.getAlgorithm());
    }
}
