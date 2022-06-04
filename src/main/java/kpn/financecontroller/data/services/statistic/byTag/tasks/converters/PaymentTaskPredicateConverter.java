package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.financecontroller.data.entities.payment.QPaymentEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final public class PaymentTaskPredicateConverter implements Function<PaymentTask, Predicate> {

    @Override
    public Predicate apply(PaymentTask paymentTask) {
        BooleanExpression expression = QPaymentEntity.paymentEntity.productEntity.in(
                paymentTask.getProducts().stream().map(p -> {
                    ProductEntity entity = new ProductEntity();
                    entity.setId(p.getId());
                    return entity;
                }).collect(Collectors.toSet())
        );
        if (paymentTask.isBeginTimeEnable()){
            expression = expression.and(QPaymentEntity.paymentEntity.createdAt.after(paymentTask.getBeginTime().minusDays(1)));
        }
        if (paymentTask.isEndTimeEnable()){
            expression = expression.and(QPaymentEntity.paymentEntity.createdAt.before(paymentTask.getEndTime().plusDays(1)));
        }

        return expression;
    }
}
