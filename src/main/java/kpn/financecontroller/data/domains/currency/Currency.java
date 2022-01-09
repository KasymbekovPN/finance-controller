package kpn.financecontroller.data.domains.currency;

import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Currency {
    private Long id;
    private String code;

    public Currency(CurrencyEntity entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
    }
}
