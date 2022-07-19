package kpn.financecontroller.data.domains.tag;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Map;
import java.util.function.Function;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractDomain<Long> {
    // TODO: 13.07.2022 move into AbstractDomain
    private static final String DEFAULT_GETTING_RESULT = "-";
    private static final Map<String, Function<GetterArg<Long>, String>> GETTERS = Map.of(
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
        setId(entity.getId());
        setName(entity.getName());
    }

    @Override
    public String getInfo() {
        return getName();
    }

    @Override
    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
        return GETTERS;
    }

    @Override
    public String toString() {
        return getName();
    }
}
