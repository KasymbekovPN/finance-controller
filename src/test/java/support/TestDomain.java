package support;

import kpn.lib.domain.AbstractDomain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TestDomain extends AbstractDomain<Long> {
    private String name;

    public TestDomain(TestEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
    }
}
