package kpn.financecontroller.gui.binding.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class BinderImplTest {
    private static final Random RANDOM = new Random();

    private static String generateKey(){
        return String.valueOf(generateValue());
    }

    private static int generateValue(){
        return RANDOM.nextInt();
    }

    @Test
    void shouldCheckBinding_ifNotBoundedYet() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        assertThat(binder.bind(generateKey(), generateValue())).isTrue();
    }

    @Test
    void shouldCheckBinding_ifBounded() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = generateKey();
        binder.bind(key, generateValue());
        assertThat(binder.bind(key, generateValue())).isFalse();
    }

    @Test
    void shouldCheckUnbinding_ifNotBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        assertThat(binder.unbind(generateKey())).isFalse();
    }

    @Test
    void shouldCheckUnbinding_ifBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = generateKey();
        binder.bind(key, generateValue());
        assertThat(binder.unbind(key)).isTrue();
    }

    @Test
    void shouldCheckIsBound_ifNotBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        assertThat(binder.isBound(generateKey())).isFalse();
    }

    @Test
    void shouldCheckIsBound_ifBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = generateKey();
        binder.bind(key, generateValue());
        assertThat(binder.isBound(key)).isTrue();
    }

    @Test
    void shouldCheckBindChanging_ifOnlyBind() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = generateKey();
        int v = generateValue();
        binder.changeBinding(key, v);
        assertThat(binder.isBound(key, v)).isTrue();
    }

    @Test
    void shouldCheckBindChanging() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = generateKey();
        int value0 = generateValue();
        binder.bind(key, value0);
        assertThat(binder.isBound(key, value0)).isTrue();

        int value1 = generateValue();
        binder.changeBinding(key, value1);
        assertThat(binder.isBound(key, value1)).isTrue();
    }

    @Test
    void shouldCheckGetting_ifAbsent() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        Optional<Integer> maybeValue = binder.get(generateKey());
        assertThat(maybeValue).isEmpty();
    }

    @Test
    void shouldCheckGetting() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = generateKey();
        int expectedValue = generateValue();
        binder.bind(key, expectedValue);

        Optional<Integer> maybeValue = binder.get(key);
        assertThat(maybeValue).isPresent();
        assertThat(maybeValue.get()).isEqualTo(expectedValue);
    }
}