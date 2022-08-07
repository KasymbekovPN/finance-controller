package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import kpn.financecontroller.data.entity.PaymentEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Payment extends AbstractDomain<Long> {
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
}
