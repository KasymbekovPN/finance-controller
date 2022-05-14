package kpn.financecontroller.initialization.entities;

import kpn.financecontroller.data.domains.payment.Currency;
import kpn.financecontroller.data.domains.payment.Measure;
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
