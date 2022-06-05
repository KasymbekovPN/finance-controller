package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class TaskAmountCheckerTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheck.csv")
    void shouldCheck(int amount, int size, boolean expectedResult) {
        TestTask[] tasks = new TestTask[amount];
        for (int i = 0; i < amount; i++) {
            tasks[0] = new TestTask();
        }

        Boolean result = new TaskAmountChecker().check(tasks, size);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    private static class TestTask implements Task {}
}