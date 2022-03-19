package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ContextManager;
import kpn.financecontroller.initialization.managers.context.ContextManagerImpl;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

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
                .code(VALUED_GENERATOR.generate(Keys.KEY, Codes.FAIL_FILE_READING))
                .arg(Keys.KEY)
                .build();
        expectedResult = Result.<String>builder()
                .success(true)
                .value(FILE_CONTENT)
                .arg(Keys.KEY)
                .build();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution_whenFileNotExist() {
        SimpleContext context = new SimpleContext();
        FileReadingTask task = new FileReadingTask(Keys.KEY, NOT_EXIST_PATH, new TestManagerCreator(), VALUED_GENERATOR);
        task.execute(context);

        Result<String> result = (Result<String>) context.get(VALUED_GENERATOR.generate(Keys.KEY, Properties.RESULT));
        assertThat(expectedResultIfFileNotExist).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution() {
        SimpleContext context = new SimpleContext();
        FileReadingTask task = new FileReadingTask(Keys.KEY, EXIST_PATH, new TestManagerCreator(), VALUED_GENERATOR);
        task.execute(context);

        Result<String> result = (Result<String>) context.get(VALUED_GENERATOR.generate(Keys.KEY, Properties.RESULT));
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    @RequiredArgsConstructor
    public enum Keys implements Valued<String>{
        KEY("key");

        @Getter
        private final String value;
    }

    public static class TestManagerCreator implements Function<Context, ContextManager>{
        @Override
        public ContextManager apply(Context context) {
            return new ContextManagerImpl(context, new ValuedStringGenerator());
        }
    }
}
