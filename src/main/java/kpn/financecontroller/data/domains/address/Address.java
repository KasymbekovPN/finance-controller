package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.domains.street.Street;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractDomain {
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
                Address domain = (Address) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            },
            "street",
            arg -> {
                Address domain = (Address) arg.getDomain();
                Street street = domain.getStreet();
                Queue<String> path = arg.getPath();
                return path.size() > 0 && street != null
                        ? street.get(path)
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private Street street;

    public Address(AddressEntity entity) {
        id = entity.getId();
        name = entity.getName();
        street = entity.getStreetEntity() != null ? new Street(entity.getStreetEntity()) : null;
    }

    @Override
    public String getInfo() {
        return getName() + ", " + street.getInfo();
    }

    @Override
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }
}
