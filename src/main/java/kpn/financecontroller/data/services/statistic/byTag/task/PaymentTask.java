package kpn.financecontroller.data.services.statistic.byTag.task;

import lombok.*;

import java.time.LocalDate;

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

    public static PaymentTask copy(PaymentTask t){
        PaymentTask task = new PaymentTask();
        task.setBeginTimeEnable(t.isBeginTimeEnable());
        task.setBeginTime(t.getBeginTime());
        task.setEndTimeEnable(t.isEndTimeEnable());
        task.setEndTime(t.getEndTime());
        return task;
    }
}
