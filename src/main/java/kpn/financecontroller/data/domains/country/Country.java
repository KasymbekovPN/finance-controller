package kpn.financecontroller.data.domains.country;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.country.CountryEntity;
import lombok.*;

import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractDomain {

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
                Country domain = (Country) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
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
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }
}
