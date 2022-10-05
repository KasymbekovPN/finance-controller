package kpn.financecontroller.gui.binding.util;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class BinderImplTest {

    @Test
    void shouldCheckBinding_ifNotBoundedYet() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = String.valueOf(new Random().nextInt());
        int value = new Random().nextInt();
        assertThat(binder.bind(key, value)).isTrue();
    }

    @Test
    void shouldCheckBinding_ifBounded() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = String.valueOf(new Random().nextInt());
        binder.bind(key, new Random().nextInt());
        assertThat(binder.bind(key, new Random().nextInt())).isFalse();
    }

    @Test
    void shouldCheckUnbinding_ifNotBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = String.valueOf(new Random().nextInt());
        assertThat(binder.unbind(key)).isFalse();
    }

    @Test
    void shouldCheckUnbinding_ifBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = String.valueOf(new Random().nextInt());
        binder.bind(key, new Random().nextInt());
        assertThat(binder.unbind(key)).isTrue();
    }

    @Test
    void shouldCheckIsBound_ifNotBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = String.valueOf(new Random().nextInt());
        assertThat(binder.isBound(key)).isFalse();
    }

    @Test
    void shouldCheckIsBound_ifBound() {
        BinderImpl<String, Integer> binder = new BinderImpl<>();

        String key = String.valueOf(new Random().nextInt());
        binder.bind(key, new Random().nextInt());
        assertThat(binder.isBound(key)).isTrue();
    }
}