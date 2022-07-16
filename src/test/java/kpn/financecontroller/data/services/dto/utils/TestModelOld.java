package kpn.financecontroller.data.services.dto.utils;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TestModelOld {
    private Long id;
    private String value;

    public TestModelOld(TestEntityOld entity) {
        id = entity.getId();
        value = entity.getValue();
    }
}
