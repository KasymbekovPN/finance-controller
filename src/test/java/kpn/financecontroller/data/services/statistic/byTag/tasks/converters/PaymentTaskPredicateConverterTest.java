package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.entity.QPaymentEntity;
import kpn.financecontroller.data.entity.ProductEntity;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTaskPredicateConverterTest {

    private static final Set<Long> PRODUCT_IDS = Set.of(1L, 2L);

    private static Set<Product> products;

    @BeforeAll
    static void beforeAll() {
        products = PRODUCT_IDS.stream().map(id -> {
            Product product = new Product();
            product.setId(id);
            return product;
        }).collect(Collectors.toSet());
    }

    @Test
    void shouldCheck_noBegin_noEnd() {
        PaymentTask task = createTask(products, null, null);
        Predicate expectedPredicate = createPredicate(task);

        Predicate predicate = new PaymentTaskPredicateConverter().apply(task);
        assertThat(expectedPredicate).isEqualTo(predicate);
    }

    @Test
    void shouldCheck_begin_noEnd() {
        PaymentTask task = createTask(products, LocalDate.now(), null);
        Predicate expectedPredicate = createPredicate(task);

        Predicate predicate = new PaymentTaskPredicateConverter().apply(task);
        assertThat(expectedPredicate).isEqualTo(predicate);
    }

    @Test
    void shouldCheck_noBegin_end() {
        PaymentTask task = createTask(products, null, LocalDate.now().plusDays(1));
        Predicate expectedPredicate = createPredicate(task);

        Predicate predicate = new PaymentTaskPredicateConverter().apply(task);
        assertThat(expectedPredicate).isEqualTo(predicate);
    }

    @Test
    void shouldCheck_begin_end() {
        PaymentTask task = createTask(products, LocalDate.now(), LocalDate.now().plusDays(1));
        Predicate expectedPredicate = createPredicate(task);

        Predicate predicate = new PaymentTaskPredicateConverter().apply(task);
        assertThat(expectedPredicate).isEqualTo(predicate);
    }

    private PaymentTask createTask(Set<Product> products, LocalDate begin, LocalDate end) {
        PaymentTask task = new PaymentTask();
        task.setProducts(products);
        if (begin != null){
            task.setBeginTimeEnable(true);
            task.setBeginTime(begin);
        }
        if (end != null){
            task.setEndTimeEnable(true);
            task.setEndTime(end);
        }
        return task;
    }

    private Predicate createPredicate(PaymentTask task) {
        BooleanExpression expression = QPaymentEntity.paymentEntity.productEntity.in(
                task.getProducts().stream().map(p -> {
                    ProductEntity entity = new ProductEntity();
                    entity.setId(p.getId());
                    return entity;
                }).collect(Collectors.toSet())
        );
        if (task.isBeginTimeEnable()){
            expression = expression.and(QPaymentEntity.paymentEntity.createdAt.after(task.getBeginTime().minusDays(1)));
        }
        if (task.isEndTimeEnable()){
            expression = expression.and(QPaymentEntity.paymentEntity.createdAt.before(task.getEndTime().plusDays(1)));
        }
        return expression;
    }
}