package kpn.financecontroller.data.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kpn.financecontroller.annotation.External;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@External
public class QAddressEntity extends EntityPathBase<AddressEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAddressEntity addressEntity = new QAddressEntity("addressEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");
    public final QStreetEntity streetEntity;

    public QAddressEntity(String variable) {
        this(AddressEntity.class, forVariable(variable), INITS);
    }

    public QAddressEntity(Path<? extends AddressEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAddressEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAddressEntity(PathMetadata metadata, PathInits inits) {
        this(AddressEntity.class, metadata, inits);
    }

    public QAddressEntity(Class<? extends AddressEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.streetEntity = inits.isInitialized("streetEntity") ? new QStreetEntity(forProperty("streetEntity")) : null;
    }
}
