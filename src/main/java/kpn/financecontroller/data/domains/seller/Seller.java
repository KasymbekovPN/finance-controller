package kpn.financecontroller.data.domains.seller;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Seller extends AbstractDomain<Long> {
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
//                Seller domain = (Seller) arg.getDomain();
//                String name = domain.getName();
//                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
//                        ? name
//                        : DEFAULT_GETTING_RESULT;
//            },
//            "url",
//            arg -> {
//                String url = ((Seller) arg.getDomain()).getUrl();
//                return arg.getPath().isEmpty() && url != null && !url.isEmpty()
//                        ? url
//                        : DEFAULT_GETTING_RESULT;
//            },
//            "description",
//            arg -> {
//                String description = ((Seller) arg.getDomain()).getDescription();
//                return arg.getPath().isEmpty() && description != null && !description.isEmpty()
//                        ? description
//                        : DEFAULT_GETTING_RESULT;
//            },
//            "address",
//            arg -> {
//                Seller domain = (Seller) arg.getDomain();
//                Address address = domain.getAddress();
//                return arg.getPath().isEmpty() && address != null
//                        ? address.getInfo()
//                        : DEFAULT_GETTING_RESULT;
//            }
//    );

    private String name;
    private String url;
    private String description;
    private Address address;

    public Seller(SellerEntity sellerEntity) {
        setId(sellerEntity.getId());
        setName(sellerEntity.getName());
        setUrl(sellerEntity.getUrl());
        setDescription(sellerEntity.getDescription());
        setAddress(sellerEntity.getAddressEntity() != null ? new Address(sellerEntity.getAddressEntity()) : null);
    }

    @Override
    public String getInfo() {
        return getName();
    }

    // TODO: 25.07.2022 del
//    @Override
//    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
//        return GETTERS;
//    }
}
