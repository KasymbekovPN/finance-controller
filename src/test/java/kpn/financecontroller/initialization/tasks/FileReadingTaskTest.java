package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManagerImpl;
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

        String success = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.SUCCESS);
        assertThat(context.get(success, Boolean.class)).isEqualTo(expectedResultIfFileNotExist.getSuccess());
        String value = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.VALUE);
        assertThat(context.get(value)).isEqualTo(expectedResultIfFileNotExist.getValue());
        String code = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.CODE);
        assertThat(context.get(code, String.class)).isEqualTo(expectedResultIfFileNotExist.getCode());
        String args = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.ARGS);
        assertThat(context.get(args)).isEqualTo(expectedResultIfFileNotExist.getArgs());
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution() {
        SimpleContext context = new SimpleContext();
        FileReadingTask task = new FileReadingTask(TestKeys.KEY, VALUED_GENERATOR, new TestManagerCreator(), EXIST_PATH);
        task.execute(context);

        String success = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.SUCCESS);
        assertThat(context.get(success, Boolean.class)).isEqualTo(expectedResult.getSuccess());
        String value = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.VALUE);
        assertThat(context.get(value)).isEqualTo(expectedResult.getValue());
        String code = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.CODE);
        assertThat(context.get(code, String.class)).isEqualTo(expectedResult.getCode());
        String args = VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT, ResultContextManagerImpl.ResultParts.ARGS);
        assertThat(context.get(args)).isEqualTo(expectedResult.getArgs());
        assertThat(task.isContinuationPossible()).isTrue();
    }
}
