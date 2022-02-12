package kpn.financecontroller.builders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConcatBuilderTest {

    @Test
    void shouldCheckEmptyBuilding() {
        ConcatBuilder builder = new ConcatBuilder();
        String buildResult = builder.build();
        assertThat(buildResult).isEmpty();
    }

    @Test
    void shouldCheckBuilding() {
        ConcatBuilder builder = new ConcatBuilder();
        String buildResult = builder
                .append("1")
                .append(2)
                .append(1.2)
                .build();
        assertThat(buildResult).isEqualTo("1, 2, 1.2");
    }

    @Test
    void shouldCheckReset() {
        ConcatBuilder builder = new ConcatBuilder();
        String buildResult = builder
                .append("1")
                .append(2)
                .append(1.2)
                .reset()
                .build();
        assertThat(buildResult).isEmpty();
    }
}