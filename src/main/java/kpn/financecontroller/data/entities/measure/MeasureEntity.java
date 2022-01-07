package kpn.financecontroller.data.entities.measure;

import kpn.financecontroller.data.domain.measure.Measure;
import kpn.financecontroller.data.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "measure")
public class MeasureEntity extends AbstractEntity {
    @NotEmpty
    @Column(unique = true)
    private String code;

    public MeasureEntity(Measure measure) {
        id = measure.getId();
        code = measure.getCode();
    }
}
