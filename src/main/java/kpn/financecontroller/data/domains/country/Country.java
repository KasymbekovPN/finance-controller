package kpn.financecontroller.data.domains.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Country extends AbstractDomain<Long>{
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
                Country domain = (Country) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;

    public Country(CountryEntity entity) {
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
}