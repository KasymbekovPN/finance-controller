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
public class QStreetEntity extends EntityPathBase<StreetEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStreetEntity streetEntity = new QStreetEntity("streetEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");
    public final QCityEntity cityEntity;

    public QStreetEntity(String variable) {
        this(StreetEntity.class, forVariable(variable), INITS);
    }

    public QStreetEntity(Path<? extends StreetEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStreetEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStreetEntity(PathMetadata metadata, PathInits inits) {
        this(StreetEntity.class, metadata, inits);
    }

    public QStreetEntity(Class<? extends StreetEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cityEntity = inits.isInitialized("cityEntity") ? new QCityEntity(forProperty("cityEntity")) : null;
    }
}
