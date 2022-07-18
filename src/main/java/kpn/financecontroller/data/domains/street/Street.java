package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.domains.city.City;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Street extends AbstractDomain<Long> {
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
                Street domain = (Street) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            },
            "city",
            arg -> {
                Street domain = (Street) arg.getDomain();
                City city = domain.getCity();
                Queue<String> path = arg.getPath();
                return path.size() > 0 && city != null
                        ? city.getInDeep(path)
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private City city;

    public Street(StreetEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setCity(new City(entity.getCityEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCity().getInfo();
    }

    @Override
    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
        return GETTERS;
    }
}
