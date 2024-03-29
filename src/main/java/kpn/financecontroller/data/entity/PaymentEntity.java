package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import kpn.financecontroller.data.domain.Payment;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "payments")
public final class PaymentEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity sellerEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private Float amount;
    private Measure measure;
    private Float price;
    private Currency currency;
    private LocalDate createdAt;

    public PaymentEntity(Payment payment) {
        setId(payment.getId());
        setProductEntity(new ProductEntity(payment.getProduct()));
        setSellerEntity(payment.getSeller() != null ? new SellerEntity(payment.getSeller()) : null);
        setAmount(payment.getAmount());
        setMeasure(payment.getMeasure());
        setPrice(payment.getPrice());
        setCurrency(payment.getCurrency());
        setCreatedAt(payment.getCreatedAt());
    }
}
