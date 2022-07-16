package support;

import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestEntity extends AbstractEntity<Long> {
    private Long id;
    private String name;

    public TestEntity(TestDomain domain) {
        setId(domain.getId());
        setName(domain.getName());
    }
}
