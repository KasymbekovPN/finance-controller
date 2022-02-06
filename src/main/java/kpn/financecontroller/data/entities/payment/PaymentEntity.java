package kpn.financecontroller.data.entities.payment;

import kpn.financecontroller.data.entities.building.BuildingEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.domains.payment.Currency;
import kpn.financecontroller.data.domains.payment.Measure;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.entities.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "payment")
public class PaymentEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity buildingEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private Float amount;
    private Measure measure;
    private Float price;
    private Currency currency;
    private LocalDate createdAt;

    public PaymentEntity(Payment payment) {
        id = payment.getId();
        productEntity = new ProductEntity(payment.getProduct());
        buildingEntity = new BuildingEntity(payment.getBuilding());
        amount = payment.getAmount();
        measure = payment.getMeasure();
        price = payment.getPrice();
        currency = payment.getCurrency();
        createdAt = payment.getCreatedAt();
    }
}
