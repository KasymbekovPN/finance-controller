package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.checker.size.SizeChecker;
import org.springframework.stereotype.Component;

@Component
final public class TaskAmountChecker implements SizeChecker<Task[]> {
    private static final int TASK_AMOUNT = 2;

    @Override
    public Boolean check(Task[] value, int size) {
        return value.length == TASK_AMOUNT;
    }
}
