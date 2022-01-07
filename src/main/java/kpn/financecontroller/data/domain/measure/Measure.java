package kpn.financecontroller.data.domain.measure;

import kpn.financecontroller.data.entities.measure.MeasureEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Measure {
    private Long id;
    private String code;

    public Measure(MeasureEntity entity) {
        id = entity.getId();
        code = entity.getCode();
    }
}
