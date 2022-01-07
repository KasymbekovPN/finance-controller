package kpn.financecontroller.data.entities.measure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MeasureEntityTest {
    @Test
    void shouldCheckIdGetting() {
        long expectedId = 123L;
        MeasureEntity measureEntity = new MeasureEntity();
        measureEntity.setId(expectedId);
        assertThat(expectedId).isEqualTo(measureEntity.getId());
    }

    @Test
    void shouldCheckCodeGetter() {
        String expectedCode = "kg";
        MeasureEntity measureEntity = new MeasureEntity();
        measureEntity.setCode(expectedCode);
        assertThat(expectedCode).isEqualTo(measureEntity.getCode());
    }
}
