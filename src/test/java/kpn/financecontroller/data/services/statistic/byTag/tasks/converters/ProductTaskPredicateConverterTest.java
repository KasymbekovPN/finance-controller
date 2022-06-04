package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.product.QProductEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ProductTaskPredicateConverterTest {

    @Test
    void shouldCheckConversion() {
        Set<Tag> tags = createTags();
        ProductTask task = crateTask(tags);
        Predicate expectedPredicate = createPredicate(tags);

        Predicate predicate = new ProductTaskPredicateConverter().apply(task);
        Assertions.assertThat(expectedPredicate).isEqualTo(predicate);
    }

    private Set<Tag> createTags() {
        return Stream.of(1L, 2L)
                .map(id -> {
                    Tag tag = new Tag();
                    tag.setId(id);
                    tag.setName("tag" + 1);
                    return tag;
                }).collect(Collectors.toSet());
    }

    private ProductTask crateTask(Set<Tag> tags) {
        ProductTask task = new ProductTask();
        task.setAllTags(false);
        task.setTags(tags);
        return task;
    }

    private Predicate createPredicate(Set<Tag> tags) {
        Set<TagEntity> entities = tags.stream().map(TagEntity::new).collect(Collectors.toSet());
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