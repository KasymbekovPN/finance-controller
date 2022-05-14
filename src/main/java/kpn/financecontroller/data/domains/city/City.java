package kpn.financecontroller.data.domains.city;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class City extends AbstractDomain {
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
                City domain = (City) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            },
            "region",
            arg -> {
                City domain = (City) arg.getDomain();
                Region region = domain.getRegion();
                Queue<String> path = arg.getPath();
                return path.size() > 0 && region != null
                        ? region.get(path)
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private Region region;

    public City(CityEntity entity) {
        id = entity.getId();
        name = entity.getName();
        region = entity.getRegionEntity() != null ? new Region(entity.getRegionEntity()) : null;
    }

    @Override
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getRegion().getInfo();
    }
}
