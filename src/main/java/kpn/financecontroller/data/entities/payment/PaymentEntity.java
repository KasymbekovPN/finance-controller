package kpn.financecontroller.data.entities.payment;

import kpn.financecontroller.data.domains.payment.Currency;
import kpn.financecontroller.data.domains.payment.Measure;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "payment")
public class PaymentEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity buildingEntity;
    private Float amount;
    private Measure measure;
    private Currency currency;
    private Date date;

    public PaymentEntity(Payment payment) {
        id = payment.getId();
        buildingEntity = new BuildingEntity(payment.getBuilding());
        amount = payment.getAmount();
        measure = payment.getMeasure();
        currency = payment.getCurrency();
        date = payment.getDate();
    }
}
