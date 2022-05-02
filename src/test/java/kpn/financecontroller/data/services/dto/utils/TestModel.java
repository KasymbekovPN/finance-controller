package kpn.financecontroller.data.services.dto.utils;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TestModel {
    private Long id;
    private String value;

    public TestModel(TestEntity entity) {
        id = entity.getId();
        value = entity.getValue();
    }
}
