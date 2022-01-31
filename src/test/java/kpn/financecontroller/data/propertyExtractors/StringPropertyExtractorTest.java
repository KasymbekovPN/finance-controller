package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

class StringPropertyExtractorTest {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";
    private static final String EMPTY_PROPERTY_VALUE = "    ";

    private static Result<String> expectedResultIfPropertyNotExist;
    private static Result<String> expectedResultIfPropertyIsEmpty;
    private static Result<String> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultIfPropertyNotExist = Result.<String>builder()
                .success(false)
                .code("file.property.extraction.ItNotExist")
                .arg(PROPERTY)
                .build();
        expectedResultIfPropertyIsEmpty = Result.<String>builder()
                .success(false)
                .code("file.property.extraction.ItEmpty")
                .arg(PROPERTY)
                .build();
        expectedResult = Result.<String>builder()
                .success(true)
                .value(VALUE)
                .code("file.property.extraction.success")
                .arg(PROPERTY)
                .arg(VALUE)
                .build();
    }

    @Test
    void shouldCheckExtractionWhenEnvVarNotExist() {
        StringPropertyExtractor extractor = new StringPropertyExtractor(createEnvironmentWithoutProperty());
        Result<String> result = extractor.extract(PROPERTY);
        assertThat(expectedResultIfPropertyNotExist).isEqualTo(result);
    }

    private Environment createEnvironmentWithoutProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PROPERTY))
                .thenReturn(null);
        return environment;
    }

    @Test
    void shouldCheckExtractionWhenEnvVarIsEmptyOrContainsOnlySpaces() {
        StringPropertyExtractor extractor = new StringPropertyExtractor(createEnvironmentWithEmptyProperty());
        Result<String> result = extractor.extract(PROPERTY);
        assertThat(expectedResultIfPropertyIsEmpty).isEqualTo(result);
    }

    private Environment createEnvironmentWithEmptyProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PROPERTY))
                .thenReturn(EMPTY_PROPERTY_VALUE);
        return environment;
    }

    @Test
    void shouldCheckPathsListExtraction() {
        StringPropertyExtractor extractor = new StringPropertyExtractor(createEnvironmentWithProperty());
        Result<String> result = extractor.extract(PROPERTY);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Environment createEnvironmentWithProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PROPERTY))
                .thenReturn(VALUE);
        return environment;
    }
}