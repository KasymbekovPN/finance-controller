package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IEPathsPropertyExtractorTest {

    private static final String PATHS = "initialEntities.paths";
    private static final List<String> RESULT_VALUE = List.of("path0", "path1", "path2");
    private static final String PROPERTY_VALUE = "path0, path1, path2";
    private static final String EMPTY_PROPERTY_VALUE = "    ";

    private static Result<List<String>> expectedResultIfPropertyNotExist;
    private static Result<List<String>> expectedResultIfPropertyIsEmpty;
    private static Result<List<String>> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultIfPropertyNotExist = Result.<List<String>>builder()
                .success(false)
                .code("propertyFile.property.notExist")
                .arg(PATHS)
                .build();
        expectedResultIfPropertyIsEmpty = Result.<List<String>>builder()
                .success(false)
                .code("propertyFile.property.empty")
                .arg(PATHS)
                .build();
        expectedResult = Result.<List<String>>builder()
                .success(true)
                .value(RESULT_VALUE)
                .build();
    }

    @Test
    void shouldCheckExtractionWhenEnvVarNotExist() {
        Environment environment = createEnvironmentWithoutProperty();
        IEPathsPropertyExtractor extractor = new IEPathsPropertyExtractor(environment);
        Result<List<String>> result = extractor.extract();
        assertThat(expectedResultIfPropertyNotExist).isEqualTo(result);
    }

    private Environment createEnvironmentWithoutProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PATHS))
                .thenReturn(null);
        return environment;
    }

    @Test
    void shouldCheckExtractionWhenEnvVarIsEmptyOrContainsOnlySpaces() {
        Environment environment = createEnvironmentWithEmptyProperty();
        IEPathsPropertyExtractor extractor = new IEPathsPropertyExtractor(environment);
        Result<List<String>> result = extractor.extract();
        assertThat(expectedResultIfPropertyIsEmpty).isEqualTo(result);
    }

    private Environment createEnvironmentWithEmptyProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PATHS))
                .thenReturn(EMPTY_PROPERTY_VALUE);
        return environment;
    }

    @Test
    void shouldCheckPathsListExtraction() {
        Environment environment = createEnvironmentWithProperty();
        IEPathsPropertyExtractor extractor = new IEPathsPropertyExtractor(environment);
        Result<List<String>> result = extractor.extract();
        assertThat(expectedResult).isEqualTo(result);
    }

    private Environment createEnvironmentWithProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PATHS))
                .thenReturn(PROPERTY_VALUE);
        return environment;
    }
}