package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.domains.street.Street;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractDomain<Long> {

    // TODO: 25.07.2022 del
//    private static final String DEFAULT_GETTING_RESULT = "-";
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
//                Address domain = (Address) arg.getDomain();
//                String name = domain.getName();
//                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
//                        ? name
//                        : DEFAULT_GETTING_RESULT;
//            },
//            "street",
//            arg -> {
//                Address domain = (Address) arg.getDomain();
//                Street street = domain.getStreet();
//                Queue<String> path = arg.getPath();
//                return path.size() > 0 && street != null
//                        ? street.getInDeep(path)
//                        : DEFAULT_GETTING_RESULT;
//            }
//    );

    private String name;
    private Street street;

    public Address(AddressEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setStreet(new Street(entity.getStreetEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + street.getInfo();
    }

    // TODO: 25.07.2022 del
//    @Override
//    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
//        return GETTERS;
//    }
}
