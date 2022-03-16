// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._context.context;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ContextImplTest {
//
//    private static final String KEY = "some.key";
//    private static final String NOT_EXIST_PROPERTY = "not.exist.property";
//    private static final String EXIST_PROPERTY = "exist.property";
//
//    private static ContextImpl context;
//
//    @BeforeAll
//    static void beforeAll() {
//        context = new ContextImpl();
//    }
//
//    @Test
//    void shouldCheckGetting_ifValueNotExist() {
//        Optional<Object> maybeObject = context.get(KEY, NOT_EXIST_PROPERTY);
//        assertThat(maybeObject).isEmpty();
//    }
//
//    @Test
//    void shouldCheckGetting_ifValueExist() {
//        String expectedValue = "value";
//        context.put(KEY, EXIST_PROPERTY, expectedValue);
//        Optional<Object> maybeObject = context.get(KEY, EXIST_PROPERTY);
//        assertThat(maybeObject).isPresent();
//        assertThat(expectedValue).isEqualTo(maybeObject.get());
//    }
//}
