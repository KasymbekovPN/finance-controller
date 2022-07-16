package kpn.financecontroller.data.services.dto.utils;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

// TODO: 16.07.2022 del
public class QTestEntityOld extends EntityPathBase<TestEntityOld> {

    public static final QTestEntityOld testEntity = new QTestEntityOld("");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath value = createString("value");

    public QTestEntityOld(String variable) {
        super(TestEntityOld.class, forVariable(variable));
    }

    public QTestEntityOld(Path<? extends TestEntityOld> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestEntityOld(PathMetadata metadata) {
        super(TestEntityOld.class, metadata);
    }
}
