package kpn.financecontroller.data.domains.payment;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.domains.product.Product;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Payment extends AbstractDomain {
    private Place place;
    private Float amount;
    private Measure measure;
    private Float price;
    private Currency currency;
    private Product product;
    private LocalDate createdAt;

    public Payment(PaymentEntity entity) {
        id = entity.getId();
        place = entity.getPlaceEntity() != null ? new Place(entity.getPlaceEntity()) : null;
        product = entity.getProductEntity() != null ? new Product(entity.getProductEntity()) : null;
        amount = entity.getAmount();
        measure = entity.getMeasure();
        price = entity.getPrice();
        currency = entity.getCurrency();
        createdAt = entity.getCreatedAt();
    }

    @Override
    public String getInfo() {
        return getId().toString();
    }
}
