package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.domains.city.City;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Street extends AbstractDomain {
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
                        ? city.get(path)
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private City city;

    public Street(StreetEntity entity) {
        id = entity.getId();
        name = entity.getName();
        city = entity.getCityEntity() != null ? new City(entity.getCityEntity()) : null;
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCity().getInfo();
    }

    @Override
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }
}
