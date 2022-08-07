package kpn.financecontroller.data.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QTagEntity extends EntityPathBase<TagEntity> {

    public static final QTagEntity tagEntity = new QTagEntity("tagEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");

    public QTagEntity(String variable) {
        super(TagEntity.class, forVariable(variable));
    }

    public QTagEntity(Path<? extends TagEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTagEntity(PathMetadata metadata) {
        super(TagEntity.class, metadata);
    }
}
