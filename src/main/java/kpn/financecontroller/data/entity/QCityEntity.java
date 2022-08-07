package kpn.financecontroller.data.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QCityEntity extends EntityPathBase<CityEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCityEntity cityEntity = new QCityEntity("cityEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");
    public final QRegionEntity regionEntity;

    public QCityEntity(String variable) {
        this(CityEntity.class, forVariable(variable), INITS);
    }

    public QCityEntity(Path<? extends CityEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCityEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCityEntity(PathMetadata metadata, PathInits inits) {
        this(CityEntity.class, metadata, inits);
    }

    public QCityEntity(Class<? extends CityEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.regionEntity = inits.isInitialized("regionEntity") ? new QRegionEntity(forProperty("regionEntity")) : null;
    }
}
