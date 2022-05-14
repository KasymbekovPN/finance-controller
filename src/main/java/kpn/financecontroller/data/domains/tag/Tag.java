package kpn.financecontroller.data.domains.tag;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.tag.TagEntity;
import lombok.*;

import java.util.Map;
import java.util.function.Function;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractDomain {
    private static final Map<String, Function<GetterArg, String>> GETTERS = Map.of(
            "id",
            arg -> {
                Long id = arg.getDomain().getId();
                return arg.getPath().isEmpty() && id != null
                        ? id.toString()
                        : DEFAULT_GETTING_RESULT;
            },
            "name",
            arg -> {
                Tag domain = (Tag) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;

    public Tag(TagEntity entity) {
        id = entity.getId();
        name = entity.getName();
    }

    @Override
    public String getInfo() {
        return getName();
    }

    @Override
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }

    @Override
    public String toString() {
        return getName();
    }
}
