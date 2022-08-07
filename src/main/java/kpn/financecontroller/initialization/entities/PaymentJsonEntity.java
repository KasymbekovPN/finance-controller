package kpn.financecontroller.initialization.entities;

import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
final public class PaymentJsonEntity extends AbstractJsonEntity{
    private Long sellerId;
    private Long productId;
    private Float amount;
    private Measure measure;
    private Float price;
    private Currency currency;
    private LocalDate createdAt;
}
