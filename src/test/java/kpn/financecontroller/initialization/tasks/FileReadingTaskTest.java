package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReadingTaskTest {

    private static final String FILE_CONTENT = "{}";
    private static final String NOT_EXIST_PATH = "kpn/financecontroller/initialization/task/notExist.json";
    private static final String EXIST_PATH = "kpn/financecontroller/initialization/task/testFile.json";
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();

    private static Result<String> expectedResultIfFileNotExist;
    private static Result<String> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultIfFileNotExist = Result.<String>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(TestKeys.KEY, Codes.FAIL_FILE_READING))
                .arg(TestKeys.KEY)
                .build();
        expectedResult = Result.<String>builder()
                .success(true)
                .value(FILE_CONTENT)
                .arg(TestKeys.KEY)
                .build();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution_whenFileNotExist() {
        SimpleContext context = new SimpleContext();
        FileReadingTask task = new FileReadingTask(TestKeys.KEY, VALUED_GENERATOR, new TestManagerCreator(), NOT_EXIST_PATH);
        task.execute(context);

        Result<String> result = (Result<String>) context.get(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT));
        assertThat(expectedResultIfFileNotExist).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution() {
        SimpleContext context = new SimpleContext();
        FileReadingTask task = new FileReadingTask(TestKeys.KEY, VALUED_GENERATOR, new TestManagerCreator(), EXIST_PATH);
        task.execute(context);

        Result<String> result = (Result<String>) context.get(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT));
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }
}
