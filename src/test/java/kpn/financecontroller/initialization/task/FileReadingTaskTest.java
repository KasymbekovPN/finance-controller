package kpn.financecontroller.initialization.task;

import kpn.financecontroller.initialization.context.ContextImpl;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReadingTaskTest {

    private static final String KEY = "some.key";
    private static final String FILE_READ_PROPERTY = "file.reading.result";
    private static final String CONTENT_NO_PATH_CODE = "context.no.path";
    private static final String STREAM_NULL_CODE = "stream.null.code";
    private static final String SUCCESS_CODE = "file.reading.success";

    private static final String NOT_EXIST_PATH = "kpn/financecontroller/initialization/task/notExist.json";
    private static final String EXIST_PATH = "kpn/financecontroller/initialization/task/testFile.json";

    private static final String FILE_CONTENT = "{}";

    private static Result<String> expectedResultWhenNoPath;
    private static Result<String> expectedResultWhenNoFile;
    private static Result<String> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenNoPath = Result.<String>builder()
                .success(false)
                .code(CONTENT_NO_PATH_CODE)
                .arg(KEY)
                .build();
        expectedResultWhenNoFile = Result.<String>builder()
                .success(false)
                .code(STREAM_NULL_CODE)
                .arg(KEY)
                .build();
        expectedResult = Result.<String>builder()
                .success(true)
                .value(FILE_CONTENT)
                .code(SUCCESS_CODE)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifContextNotContainPath() {
        FileReadingTask task = new FileReadingTask(KEY);
        ContextImpl context = new ContextImpl();
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, FILE_READ_PROPERTY);
        assertThat(maybeResult).isPresent();
        Result<String> result = (Result<String>) maybeResult.get();
        assertThat(expectedResultWhenNoPath).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_whenFileNotExist() {
        FileReadingTask task = new FileReadingTask(KEY);

        ContextImpl context = new ContextImpl();
        context.put(KEY, "file.path", NOT_EXIST_PATH);

        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, FILE_READ_PROPERTY);
        assertThat(maybeResult).isPresent();
        Result<String> result = (Result<String>) maybeResult.get();
        assertThat(expectedResultWhenNoFile).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        FileReadingTask task = new FileReadingTask(KEY);

        ContextImpl context = new ContextImpl();
        context.put(KEY, "file.path", EXIST_PATH);

        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, FILE_READ_PROPERTY);
        assertThat(maybeResult).isPresent();
        Result<String> result = (Result<String>) maybeResult.get();
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }
}
