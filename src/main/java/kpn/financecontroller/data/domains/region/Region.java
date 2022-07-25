package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Region extends AbstractDomain<Long> {
    // TODO: 25.07.2022 del
//    private static final String DEFAULT_GETTING_RESULT = "-";
//
//    private static final Map<String, Function<GetterArg<Long>, String>> GETTERS = Map.of(
//            "id",
//            arg -> {
//                Long id = arg.getDomain().getId();
//                return arg.getPath().isEmpty() && id != null
//                        ? id.toString()
//                        : DEFAULT_GETTING_RESULT;
//            },
//            "name",
//            arg -> {
//                Region domain = (Region) arg.getDomain();
//                String name = domain.getName();
//                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
//                        ? name
//                        : DEFAULT_GETTING_RESULT;
//            },
//            "country",
//            arg -> {
//                Region domain = (Region) arg.getDomain();
//                Country country = domain.getCountry();
//                Queue<String> path = arg.getPath();
//                return path.size() > 0 && country != null
//                        ? country.getInDeep(path)
//                        : DEFAULT_GETTING_RESULT;
//            }
//    );

    private String name;
    private Country country;

    public Region(RegionEntity regionEntity) {
        setId(regionEntity.getId());
        setName(regionEntity.getName());
        setCountry(new Country(regionEntity.getCountryEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCountry().getInfo();
    }

    // TODO: 25.07.2022 del
//    @Override
//    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
//        return GETTERS;
//    }
}
