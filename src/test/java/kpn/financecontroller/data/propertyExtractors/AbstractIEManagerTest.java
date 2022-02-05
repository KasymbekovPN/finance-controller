package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 05.02.2022 del
class AbstractIEManagerTest {

    // TODO: 03.02.2022 restore???
//    private static final String NAME = "name";
//    private static final String CODE = "code";
//
//    private static Result<Void> expectedOnFailGetting;
//    private static Result<Void> expectedOnGetting;
//    private static Result<Void> expectedOnDirectoryNull;
//    private static Result<Void> expectedNotImplPathCalcMethod;
//    private static Result<Void> expectedNotImplFillCollectorMethod;
//
//    @BeforeAll
//    static void beforeAll() {
//        expectedOnFailGetting = Result.<Void>builder()
//                .success(false)
//                .code("ieManager.getting.fail.notInit")
//                .arg(NAME)
//                .build();
//        expectedOnGetting = Result.<Void>builder()
//                .success(true)
//                .code(CODE)
//                .build();
//        expectedOnDirectoryNull = Result.<Void>builder()
//                .success(false)
//                .code("inManager.checking.directory.null")
//                .build();
//        expectedNotImplPathCalcMethod = Result.<Void>builder()
//                .success(false)
//                .code("ieManager.method.calculatePath.notImplemented")
//                .build();
//        expectedNotImplFillCollectorMethod = Result.<Void>builder()
//                .success(false)
//                .code("ieManager.method.fillCollector.notImplemented")
//                .build();
//    }
//
//    @Test
//    void shouldCheckNonInitResultGetting() {
//        TestIEManagerForGetting manager = new TestIEManagerForGetting(null, NAME);
//        Result<Void> result = manager.get();
//        assertThat(expectedOnFailGetting).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheckResultGetting() {
//        TestIEManagerForGetting manager = new TestIEManagerForGetting(null, NAME);
//        Result<Void> result = manager.run().get();
//        assertThat(expectedOnGetting).isEqualTo(result);
//    }
//
//    private static class TestIEManagerForGetting extends AbstractIEManager<Long, String>{
//        public TestIEManagerForGetting(ResourceFileReader reader, String name) {
//            super(reader, name);
//        }
//
//        @Override
//        public IEManager<Long, String> run() {
//            result = Result.<Void>builder()
//                    .success(true)
//                    .code(CODE)
//                    .build();
//            return super.run();
//        }
//    }
//
//    @Test
//    void shouldCheckDirectoryChecking() {
//        TestIEManagerForDirectoryChecking manager = new TestIEManagerForDirectoryChecking(null, NAME);
//        Result<Void> result = manager.run().get();
//        assertThat(expectedOnDirectoryNull).isEqualTo(result);
//    }
//
//    private static class TestIEManagerForDirectoryChecking extends AbstractIEManager<Long, String>{
//        public TestIEManagerForDirectoryChecking(ResourceFileReader reader, String name) {
//            super(reader, name);
//        }
//    }
//
//    @Test
//    void shouldCheckPathCalcMethod() {
//        TestIEManagerForPathCalcMethod manager = new TestIEManagerForPathCalcMethod(null, NAME);
//        Result<Void> result = manager.directory("").run().get();
//        assertThat(expectedNotImplPathCalcMethod).isEqualTo(result);
//    }
//
//    private static class TestIEManagerForPathCalcMethod extends AbstractIEManager<Long, String>{
//        public TestIEManagerForPathCalcMethod(ResourceFileReader reader, String name) {
//            super(reader, name);
//        }
//    }
//
//    @Test
//    void shouldCheckFillCollectorMethod() {
////        new TestIEManagerForFillCollectorMethod()
//    }
}