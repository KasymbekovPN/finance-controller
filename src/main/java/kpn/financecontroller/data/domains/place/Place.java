package kpn.financecontroller.data.domains.place;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import lombok.*;

import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Place extends AbstractDomain {
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
                Place domain = (Place) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            },
            "online",
            arg -> {
                Place domain = (Place) arg.getDomain();
                return arg.getPath().isEmpty() ? String.valueOf(domain.isOnline()) : DEFAULT_GETTING_RESULT;
            },
            "address",
            arg -> {
                Place domain = (Place) arg.getDomain();
                Address address = domain.getAddress();
                return arg.getPath().isEmpty() && address != null
                        ? address.getInfo()
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private String name;
    private boolean online;
    private Address address;

    public Place(PlaceEntity placeEntity) {
        id = placeEntity.getId();
        name = placeEntity.getName();
        online = placeEntity.isOnline();
        address = placeEntity.getAddressEntity() != null ? new Address(placeEntity.getAddressEntity()) : null;
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
