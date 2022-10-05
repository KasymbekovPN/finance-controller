package kpn.financecontroller.gui.binding.editor;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 05.10.2022 del
class EditorToActionBinderImplTest {
    private static final String KEY = "key";

    @RepeatedTest(100)
    void shouldCheckKeyWithoutRegistration() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();

        String key = String.valueOf(new Random().nextInt());
        assertThat(binder.checkKey(key)).isFalse();
    }

    @Test
    void shouldCheckAfterRegistration() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();

        binder.registerKey(KEY);
        assertThat(binder.checkKey(KEY)).isTrue();
    }

    @Test
    void shouldCheckAfterUnregistration() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();

        binder.registerKey(KEY);
        binder.unregister(KEY);
        assertThat(binder.checkKey(KEY)).isFalse();
    }

    @RepeatedTest(100)
    void shouldCheckBindingWithoutBinding() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();

        long e = (long) new Random().nextInt();
        assertThat(binder.checkBinding(e)).isFalse();
    }

    @Test
    void shouldCheckBinding() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();
        long e = (long) new Random().nextInt();
        binder.registerKey(KEY);

        assertThat(binder.bind(KEY, e)).isTrue();
    }

    @Test
    void shouldCheckBindingIfKeyAbsent() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();
        long e = (long) new Random().nextInt();

        assertThat(binder.bind(KEY, e)).isFalse();
    }

    @Test
    void shouldCheckBindingIfKeyBusy() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();
        binder.registerKey(KEY);
        binder.bind(KEY, (long) new Random().nextInt());
        long e = (long) new Random().nextInt();

        assertThat(binder.bind(KEY, e)).isFalse();
    }

    @Test
    void shouldCheckAfterUnbinding() {
        EditorToActionBinderImpl binder = new EditorToActionBinderImpl();
        binder.registerKey(KEY);
        long e = (long) new Random().nextInt();
        binder.bind(KEY, e);
        binder.unbind(e);

        assertThat(binder.checkBinding(e)).isFalse();
    }
}