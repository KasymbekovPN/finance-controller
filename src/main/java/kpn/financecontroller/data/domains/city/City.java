package kpn.financecontroller.data.domains.city;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class City extends AbstractDomain<Long> {
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
                        ? region.getInDeep(path)
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private Region region;

    public City(CityEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setRegion(new Region(entity.getRegionEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getRegion().getInfo();
    }

    @Override
    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
        return GETTERS;
    }
}
