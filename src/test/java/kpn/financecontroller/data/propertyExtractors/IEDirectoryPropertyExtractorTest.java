package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

class IEDirectoryPropertyExtractorTest {

    private final static String PROPERTY = "initialEntities.directory";
    private final static String VALUE = "value";
    private final static String EMPTY_VALUE = "          ";

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
    void shouldCheckIfPropertyNotExist() {
        IEDirectoryPropertyExtractor extractor = new IEDirectoryPropertyExtractor(createEnvironmentWithoutProperty());
        Assertions.assertThat(extractor.extract()).isEqualTo(expectedResultIfPropertyNotExist);
    }

    private Environment createEnvironmentWithoutProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PROPERTY))
                .thenReturn(null);
        return environment;
    }

    @Test
    void shouldCheckIfPropertyIsEmpty() {
        IEDirectoryPropertyExtractor extractor = new IEDirectoryPropertyExtractor(createEnvironmentWithEmptyProperty());
        Assertions.assertThat(extractor.extract()).isEqualTo(expectedResultIfPropertyIsEmpty);
    }

    private Environment createEnvironmentWithEmptyProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PROPERTY))
                .thenReturn(EMPTY_VALUE);
        return environment;
    }

    @Test
    void shouldCheckIfProperty() {
        IEDirectoryPropertyExtractor extractor = new IEDirectoryPropertyExtractor(createEnvironmentWithProperty());
        Assertions.assertThat(extractor.extract()).isEqualTo(expectedResult);
    }

    private Environment createEnvironmentWithProperty() {
        Environment environment = Mockito.mock(Environment.class);
        Mockito
                .when(environment.getProperty(PROPERTY))
                .thenReturn(VALUE);
        return environment;
    }
}