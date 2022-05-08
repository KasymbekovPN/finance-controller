package kpn.financecontroller.data.domains.tag;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.tag.TagEntity;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractDomain {
    private static final Map<String, Function<Tag, String>> GETTERS = Map.of(
            "id",
            tag -> {return tag.getId() != null ? tag.getId().toString() : DEFAULT_GETTING_RESULT;},
            "name",
            tag -> {
                String name = tag.getName();
                return name != null && !name.isEmpty() ? name : DEFAULT_GETTING_RESULT;
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
    public String get(Queue<String> path) {
        if (path.size() == 1){
            String key = path.poll();
            if (GETTERS.containsKey(key)){
                return GETTERS.get(key).apply(this);
            }
        }
        return DEFAULT_GETTING_RESULT;
    }

    @Override
    public String toString() {
        return getName();
    }
}
