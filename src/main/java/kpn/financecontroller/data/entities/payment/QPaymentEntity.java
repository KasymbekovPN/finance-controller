package kpn.financecontroller.data.entities.payment;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import kpn.financecontroller.data.domains.payment.Currency;
import kpn.financecontroller.data.domains.payment.Measure;
import kpn.financecontroller.data.entities.product.QProductEntity;
import kpn.financecontroller.data.entities.seller.QSellerEntity;

import java.time.LocalDate;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QPaymentEntity extends EntityPathBase<PaymentEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentEntity paymentEntity = new QPaymentEntity("paymentEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final QSellerEntity sellerEntity;
    public final QProductEntity productEntity;
    public final NumberPath<Float> amount = createNumber("amount", Float.class);
    public final EnumPath<Measure> measure = createEnum("measure", Measure.class);
    public final NumberPath<Float> price = createNumber("price", Float.class);
    public final EnumPath<Currency> currency = createEnum("currency", Currency.class);
    public final DatePath<LocalDate> createdAt = createDate("createdAt", LocalDate.class);

    public QPaymentEntity(String variable) {
        this(PaymentEntity.class, forVariable(variable), INITS);
    }

    public QPaymentEntity(Path<? extends PaymentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentEntity(PathMetadata metadata, PathInits inits) {
        this(PaymentEntity.class, metadata, inits);
    }

    public QPaymentEntity(Class<? extends PaymentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sellerEntity = inits.isInitialized("sellerEntity") ? new QSellerEntity(forProperty("sellerEntity")) : null;
        this.productEntity = inits.isInitialized("productEntity") ? new QProductEntity(forProperty("productEntity")) : null;
    }
}
