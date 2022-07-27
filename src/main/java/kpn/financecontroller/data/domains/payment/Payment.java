package kpn.financecontroller.data.domains.payment;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.domains.product.Product;
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
