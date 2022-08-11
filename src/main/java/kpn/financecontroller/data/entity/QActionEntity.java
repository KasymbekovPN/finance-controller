package kpn.financecontroller.data.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QActionEntity extends EntityPathBase<ActionEntity> {

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActionEntity actionEntity = new QActionEntity("actionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath description = createString("description");
    public final StringPath algorithm = createString("algorithm");

    public QActionEntity(String variable) {
        this(ActionEntity.class, forVariable(variable), INITS);
    }

    public QActionEntity(Path<? extends ActionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActionEntity(PathMetadata metadata, PathInits inits) {
        this(ActionEntity.class, metadata, inits);
    }

    public QActionEntity(Class<? extends ActionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
    }
}
