package kpn.financecontroller.gui.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalAttributeProcessorTest {

    private final static Function<TestObject, Boolean> CALCULATOR = TestObject::getStatus;
    private final static Consumer<TestObject> CLEARER = TestObject::clear;

    @Test
    void shouldCheckDefaultStatusGetting() {
        Boolean status = new OptionalAttributeProcessor<TestObject>(CALCULATOR, CLEARER).getStatus();

        assertThat(status).isFalse();
    }

    @Test
    void shouldCheckCalculation() {
        OptionalAttributeProcessor<TestObject> processor = new OptionalAttributeProcessor<>(CALCULATOR, CLEARER);
        boolean expectedStatus = true;
        processor.calculate(new TestObject(expectedStatus));

        assertThat(expectedStatus).isEqualTo(processor.getStatus());
    }

    @Test
    void shouldCheckCleaning_whenStatusTrue() {
        OptionalAttributeProcessor<TestObject> processor = new OptionalAttributeProcessor<>(CALCULATOR, CLEARER);
        TestObject attribute = new TestObject(true);
        processor.calculate(attribute);
        processor.clear(attribute);

        assertThat(attribute.isCleaned()).isFalse();
    }

    @Test
    void shouldCheckCleaning_whenStatusFalse() {
        OptionalAttributeProcessor<TestObject> processor = new OptionalAttributeProcessor<>(CALCULATOR, CLEARER);
        TestObject attribute = new TestObject(false);
        processor.calculate(attribute);
        processor.clear(attribute);

        assertThat(attribute.isCleaned()).isTrue();
    }

    @RequiredArgsConstructor
    @Getter
    private static class TestObject{
        private final Boolean status;
        private boolean cleaned;

        public void clear() {
            cleaned = true;
        }
    }
}