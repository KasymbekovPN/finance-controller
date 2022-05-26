package kpn.financecontroller.data.entities.seller;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kpn.financecontroller.data.entities.address.QAddressEntity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QSellerEntity extends EntityPathBase<SellerEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSellerEntity sellerEntity = new QSellerEntity("sellerEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");
    public final StringPath url = createString("url");
    public final StringPath description = createString("description");
    public final QAddressEntity addressEntity;

    public QSellerEntity(String variable) {
        this(SellerEntity.class, forVariable(variable), INITS);
    }

    public QSellerEntity(Path<? extends SellerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSellerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSellerEntity(PathMetadata metadata, PathInits inits) {
        this(SellerEntity.class, metadata, inits);
    }

    public QSellerEntity(Class<? extends SellerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addressEntity = inits.isInitialized("addressEntity") ? new QAddressEntity(forProperty("addressEntity")) : null;
    }
}
