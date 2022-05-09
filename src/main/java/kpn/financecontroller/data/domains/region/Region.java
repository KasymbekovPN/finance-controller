package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.region.RegionEntity;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Region extends AbstractDomain {
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
                Region domain = (Region) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            },
            "country",
            arg -> {
                Region domain = (Region) arg.getDomain();
                Country country = domain.getCountry();
                Queue<String> path = arg.getPath();
                return path.size() > 0 && country != null
                        ? country.get(path)
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private Country country;

    public Region(RegionEntity regionEntity) {
        id = regionEntity.getId();
        name = regionEntity.getName();
        country = new Country(regionEntity.getCountryEntity());
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCountry().getInfo();
    }

    @Override
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }
}
