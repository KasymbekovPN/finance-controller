package kpn.financecontroller.data.domains.country;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.country.CountryEntity;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractDomain {

    private static final Map<String, Function<Country, String>> GETTERS = Map.of(
            "id",
            country -> {return country.getId() != null ? country.getId().toString() : DEFAULT_GETTING_RESULT;},
            "name",
            country -> {
                String name = country.getName();
                return name != null && !name.isEmpty() ? name : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;

    public Country(CountryEntity entity) {
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
}
