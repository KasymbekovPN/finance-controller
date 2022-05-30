package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;

class PaymentTaskCheckerTest {

    @ParameterizedTest
    @CsvFileSource(resources = "paymentTaskChecker_shouldCheckChecking.csv")
    void shouldCheckChecking(boolean beginTimeEnabled,
                             LocalDate beginTime,
                             boolean endTimeEnabled,
                             LocalDate endTime,
                             String expectedCode,
                             boolean expectedSuccess) {
        PaymentTask task = createTask(beginTimeEnabled, beginTime, endTimeEnabled, endTime);
        Result<PaymentTask> expectedResult = createResult(expectedSuccess, task, expectedCode);

        Result<PaymentTask> result = new PaymentTaskChecker().check(task);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    private PaymentTask createTask(boolean beginTimeEnabled, LocalDate beginTime, boolean endTimeEnabled, LocalDate endTime) {
        PaymentTask task = new PaymentTask();
        task.setBeginTimeEnable(beginTimeEnabled);
        task.setBeginTime(beginTime);
        task.setEndTimeEnable(endTimeEnabled);
        task.setEndTime(endTime);
        return task;
    }

    private Result<PaymentTask> createResult(boolean success, PaymentTask task, String code) {
        ImmutableResult.Builder<PaymentTask> builder = ImmutableResult.<PaymentTask>builder()
                .success(success)
                .value(task);
        if (code != null){
            builder.code(code);
        }
        return builder.build();
    }
}