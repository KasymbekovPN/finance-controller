package kpn.financecontroller.data.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import kpn.financecontroller.annotation.External;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@External
public class QProductEntity extends EntityPathBase<ProductEntity> {

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath name = createString("name");
    public final SetPath<TagEntity, QTagEntity> tagEntities
            = this.<TagEntity, QTagEntity>createSet("tagEntities", TagEntity.class, QTagEntity.class, PathInits.DIRECT2);

    public QProductEntity(String variable) {
        super(ProductEntity.class, forVariable(variable));
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductEntity(PathMetadata metadata) {
        super(ProductEntity.class, metadata);
    }
}
