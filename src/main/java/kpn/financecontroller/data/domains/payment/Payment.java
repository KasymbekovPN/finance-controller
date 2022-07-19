package kpn.financecontroller.data.domains.payment;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.domains.product.Product;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Payment extends AbstractDomain<Long> {
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
            "product",
            arg -> {
                Product product = ((Payment) arg.getDomain()).getProduct();
                Queue<String> path = arg.getPath();
                return !path.isEmpty() && product != null
                        ? product.getInDeep(path)
                        : DEFAULT_GETTING_RESULT;
            },
            "price",
            arg -> {
                Payment domain = (Payment) arg.getDomain();
                Float price = domain.getPrice();
                return arg.getPath().isEmpty() && price != null
                        ? String.valueOf(price)
                        : DEFAULT_GETTING_RESULT;
            },
            "currency",
            arg -> {
                Payment domain = (Payment) arg.getDomain();
                Currency currency = domain.getCurrency();
                return arg.getPath().isEmpty() && currency != null
                        ? currency.name()
                        : DEFAULT_GETTING_RESULT;
            },
            "amount",
            arg -> {
                Payment domain = (Payment) arg.getDomain();
                Float amount = domain.getAmount();
                return arg.getPath().isEmpty() && amount != null
                        ? String.valueOf(amount)
                        : DEFAULT_GETTING_RESULT;
            },
            "measure",
            arg -> {
                Payment domain = (Payment) arg.getDomain();
                Measure measure = domain.getMeasure();
                return arg.getPath().isEmpty() && measure != null
                        ? measure.name()
                        : DEFAULT_GETTING_RESULT;
            },
            "seller",
            arg -> {
                Seller seller = ((Payment) arg.getDomain()).getSeller();
                Queue<String> path = arg.getPath();
                return !path.isEmpty() && seller != null
                        ? seller.getInDeep(path)
                        : DEFAULT_GETTING_RESULT;
            },
            "createdAt",
            arg -> {
                LocalDate createdAt = ((Payment) arg.getDomain()).getCreatedAt();
                return arg.getPath().isEmpty() && createdAt != null
                        ? createdAt.toString()
                        : DEFAULT_GETTING_RESULT;
            }
    );

    private Seller seller;
    private Float amount;
    private Measure measure;
    private Float price;
    private Currency currency;
    private Product product;
    private LocalDate createdAt;

    public Payment(PaymentEntity entity) {
        setId(entity.getId());
        setSeller(entity.getSellerEntity() != null ? new Seller(entity.getSellerEntity()) : null);
        setProduct(entity.getProductEntity() != null ? new Product(entity.getProductEntity()) : null);
        setAmount(entity.getAmount());
        setMeasure(entity.getMeasure());
        setPrice(entity.getPrice());
        setCurrency(entity.getCurrency());
        setCreatedAt(entity.getCreatedAt());
    }

    @Override
    public String getInfo() {
        return getId().toString();
    }

    @Override
    protected Map<String, Function<GetterArg<Long>, String>> takeGetters() {
        return GETTERS;
    }
}
