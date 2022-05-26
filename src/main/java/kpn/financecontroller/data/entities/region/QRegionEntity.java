package kpn.financecontroller.data.entities.region;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kpn.financecontroller.data.entities.country.QCountryEntity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QRegionEntity extends EntityPathBase<RegionEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegionEntity regionEntity = new QRegionEntity("regionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");
    public final QCountryEntity countryEntity;

    public QRegionEntity(String variable) {
        this(RegionEntity.class, forVariable(variable), INITS);
    }

    public QRegionEntity(Path<? extends RegionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegionEntity(PathMetadata metadata, PathInits inits) {
        this(RegionEntity.class, metadata, inits);
    }

    public QRegionEntity(Class<? extends RegionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.countryEntity = inits.isInitialized("countryEntity") ? new QCountryEntity(forProperty("countryEntity")) : null;
    }
}
