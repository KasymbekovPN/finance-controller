package kpn.financecontroller.data.domains.payment;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import lombok.*;

import java.util.Date;

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
    private Currency currency;
    private Date date;

    public Payment(PaymentEntity entity) {
        id = entity.getId();
        building = entity.getBuildingEntity() != null ? new Building(entity.getBuildingEntity()) : null;
        amount = entity.getAmount();
        measure = entity.getMeasure();
        currency = entity.getCurrency();
        date = entity.getDate();
    }
}
