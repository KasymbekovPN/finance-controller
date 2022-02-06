package kpn.financecontroller.data.domains.payment;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.domains.product.Product;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Payment {
    private Long id;
    private Building building;
    private Float amount;
    private Measure measure;
    private Float price;
    private Currency currency;
    private Product product;
    private LocalDate createdAt;

    public Payment(PaymentEntity entity) {
        id = entity.getId();
        building = entity.getBuildingEntity() != null ? new Building(entity.getBuildingEntity()) : null;
        product = entity.getProductEntity() != null ? new Product(entity.getProductEntity()) : null;
        amount = entity.getAmount();
        measure = entity.getMeasure();
        price = entity.getPrice();
        currency = entity.getCurrency();
        createdAt = entity.getCreatedAt();
    }
}
