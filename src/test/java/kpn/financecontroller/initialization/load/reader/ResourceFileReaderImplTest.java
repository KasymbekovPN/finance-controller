package kpn.financecontroller.initialization.load.reader;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceFileReaderImplTest {

    private static final String WRONG_PATH = "wrong_path.txt";
    private static final String CONTENT = "content";
    private static final String PATH = "kpn/financecontroller/data/propertyExtractors/file.txt";

    private static ResourceFileReaderImpl reader;
    private static Result<String> expectedOnWrongReading;
    private static Result<String> expectedOnReading;
    private static Result<String> expectedOnNullPath;

    @BeforeAll
    static void beforeAll() {
        reader = new ResourceFileReaderImpl();
        expectedOnNullPath = Result.<String>builder()
                .success(false)
                .code("resource.file.read.fail.nullPath")
                .arg(null)
                .build();
        expectedOnWrongReading = Result.<String>builder()
                .success(false)
                .code("resource.file.read.fail")
                .arg(WRONG_PATH)
                .build();
        expectedOnReading = Result.<String>builder()
                .success(true)
                .value(CONTENT)
                .code("resource.file.read.success")
                .arg(PATH)
                .build();
    }

    @Test
    void shouldCheckNullPathReading() {
        Result<String> result = reader.read(null);
        assertThat(expectedOnNullPath).isEqualTo(result);
    }

    @Test
    void shouldCheckFailReading() {
        Result<String> result = reader.read(WRONG_PATH);
        assertThat(expectedOnWrongReading).isEqualTo(result);
    }

    @Test
    void shouldCheckReading() {
        Result<String> result = reader.read(PATH);
        assertThat(expectedOnReading).isEqualTo(result);
    }
}