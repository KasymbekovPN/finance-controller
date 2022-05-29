package kpn.financecontroller.data.services.statistic.byTag.tasks.task;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;

class PaymentTaskTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckCopying.csv")
    void shouldCheckCopying(boolean beginTimeEnable, boolean endTimeEnable) {
        PaymentTask task = createTask(beginTimeEnable, endTimeEnable);
        PaymentTask copied = PaymentTask.copy(task);

        Assertions.assertThat(task).isEqualTo(copied);
    }

    private PaymentTask createTask(boolean beginTimeEnable, boolean endTimeEnable) {
        LocalDate currentLocalDate = LocalDate.now();

        PaymentTask task = new PaymentTask();
        task.setBeginTimeEnable(beginTimeEnable);
        if (beginTimeEnable){
            task.setBeginTime(currentLocalDate);
        }
        task.setEndTimeEnable(endTimeEnable);
        if (endTimeEnable){
            task.setEndTime(currentLocalDate.plusDays(1));
        }
        return task;
    }
}