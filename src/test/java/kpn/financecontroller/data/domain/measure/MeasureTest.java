package kpn.financecontroller.data.domain.measure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MeasureTest {

    private static final Long EXPECTED_ID = 123L;
    private static final String EXPECTED_CODE = "some.code";

    @Test
    void shouldCheckIdGetting() {
        Measure measure = new Measure();
        measure.setId(EXPECTED_ID);
        assertThat(EXPECTED_ID).isEqualTo(measure.getId());
    }

    @Test
    void shouldCheckCodeGetting() {
        Measure measure = new Measure();
        measure.setCode(EXPECTED_CODE);
        assertThat(EXPECTED_CODE).isEqualTo(measure.getCode());
    }
}
