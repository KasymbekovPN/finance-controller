package kpn.financecontroller.data.services.statistic.byTag.tasks.task;

import kpn.financecontroller.data.domains.product.Product;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
final public class PaymentTask implements Task{
    private boolean beginTimeEnable;
    private LocalDate beginTime;
    private boolean endTimeEnable;
    private LocalDate endTime;
    private Set<Product> products;

    public static PaymentTask copy(PaymentTask t){
        PaymentTask task = new PaymentTask();
        task.setBeginTimeEnable(t.isBeginTimeEnable());
        task.setBeginTime(t.getBeginTime());
        task.setEndTimeEnable(t.isEndTimeEnable());
        task.setEndTime(t.getEndTime());
        task.setProducts(t.getProducts());
        return task;
    }
}
