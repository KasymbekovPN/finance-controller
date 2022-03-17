package kpn.financecontroller.initialization.generators.valued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValuedStringGeneratorTest {

    @Test
    void shouldCheckGeneration() {
        String expectedLine = TestValued.TEST0.getValue() + "..." + TestValued.TEST1.getValue();
        assertThat(expectedLine).isEqualTo(new ValuedStringGenerator().generate(TestValued.TEST0, TestValued.TEST1));
    }

    @RequiredArgsConstructor
    private enum TestValued implements Valued<String>{
        TEST0("test0"),
        TEST1("test1");

        @Getter
        private final String value;
    }
}