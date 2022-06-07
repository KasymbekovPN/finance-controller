package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.financecontroller.data.entities.product.QProductEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final public class ProductTaskPredicateConverter implements Function<ProductTask, Predicate> {

    @Override
    public Predicate apply(ProductTask productTask) {
        Set<TagEntity> entities = productTask.getTags().stream().map(TagEntity::new).collect(Collectors.toSet());
        BooleanExpression expression = null;
        for (TagEntity entity : entities) {
            if (expression == null){
                expression = QProductEntity.productEntity.tagEntities.contains(entity);
            } else {
                expression = expression.or(QProductEntity.productEntity.tagEntities.contains(entity));
            }
        }
        return expression;
    }
}
