package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadingTaskTest {

    private static final String FILE_CONTENT = "{}";
    private static final String NOT_EXIST_PATH = "kpn/financecontroller/initialization/task/notExist.json";
    private static final String EXIST_PATH = "kpn/financecontroller/initialization/task/testFile.json";
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();

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
        ReadingTask task = new ReadingTask();
        task.setKey(TestKeys.KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(new TestManagerCreator());
        task.setPath(NOT_EXIST_PATH);

        DefaultContext context = new DefaultContext();
        task.execute(context);

        Result<String> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.FILE_READING_RESULT, String.class);
        assertThat(expectedResultIfFileNotExist).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution() {
        ReadingTask task = new ReadingTask();
        task.setKey(TestKeys.KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(new TestManagerCreator());
        task.setPath(EXIST_PATH);

        DefaultContext context = new DefaultContext();
        task.execute(context);

        Result<String> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.FILE_READING_RESULT, String.class);
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }
}
