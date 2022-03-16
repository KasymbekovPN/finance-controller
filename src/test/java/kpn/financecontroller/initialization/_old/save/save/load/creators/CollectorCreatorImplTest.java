// TODO: 21.02.2022 restore
//package kpn.financecontroller.initialization.load.creators;
//
//import com.google.gson.JsonSyntaxException;
//import kpn.financecontroller.initialization.collectors.LoadDataCollector;
//import kpn.financecontroller.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class CollectorCreatorImplTest {
//
//    private static final String CREATOR_ID = "id";
//
//    private static Result<LoadDataCollector<Long, String>> expectedResultWhenCollectorResultNull;
//    private static Result<LoadDataCollector<Long, String>> expectedResultWhenCheckGetting;
//    private static Result<LoadDataCollector<Long, String>> expectedResultWhenParsingFail;
//    private static Result<LoadDataCollector<Long, String>> expectedResult;
//
//    @BeforeAll
//    static void beforeAll() {
//        expectedResultWhenCollectorResultNull = Result.<LoadDataCollector<Long, String>>builder()
//                .success(false)
//                .code("creator.collector.result.null")
//                .arg(CREATOR_ID)
//                .build();
//        expectedResultWhenCheckGetting = Result.<LoadDataCollector<Long, String>>builder()
//                .success(true)
//                .arg(CREATOR_ID)
//                .build();
//        expectedResultWhenParsingFail = Result.<LoadDataCollector<Long, String>>builder()
//                .success(false)
//                .code("creator.collector.parsing.fail")
//                .arg(CREATOR_ID)
//                .build();
//        expectedResult = Result.<LoadDataCollector<Long, String>>builder()
//                .success(true)
//                .code("creator.collector.parsing.success")
//                .arg(CREATOR_ID)
//                .build();
//    }
//
//    @Test
//    void shouldCheckGettingWhenLastResultIsNull() {
//        CollectorCreatorForNullResultGettingImpl creator = new CollectorCreatorForNullResultGettingImpl(CREATOR_ID);
//        Result<LoadDataCollector<Long, String>> result = creator.get();
//        assertThat(expectedResultWhenCollectorResultNull).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheckGetting() {
//        CollectorCreatorForGettingImpl creator = new CollectorCreatorForGettingImpl(CREATOR_ID);
//        creator.create("");
//        Result<LoadDataCollector<Long, String>> result = creator.get();
//        assertThat(expectedResultWhenCheckGetting).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheckCreationWhenParsingFail() {
//        CollectorCreatorForParsingFailImpl creator = new CollectorCreatorForParsingFailImpl(CREATOR_ID);
//        Result<LoadDataCollector<Long, String>> result = creator.create("");
//        assertThat(expectedResultWhenParsingFail).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheckCreation() {
//        CollectorCreatorForSuccessImpl creator = new CollectorCreatorForSuccessImpl(CREATOR_ID);
//        Result<LoadDataCollector<Long, String>> result = creator.create("");
//        assertThat(expectedResult).isEqualTo(result);
//    }
//
//    private static class CollectorCreatorForNullResultGettingImpl extends CollectorCreatorImpl<Long, String> {
//        public CollectorCreatorForNullResultGettingImpl(String id) {
//            super(id);
//        }
//
//        @Override
//        protected LoadDataCollector<Long, String> createCollector(String source) {
//            return null;
//        }
//    }
//
//    private static class CollectorCreatorForGettingImpl extends CollectorCreatorImpl<Long, String> {
//        public CollectorCreatorForGettingImpl(String creatorId) {
//            super(creatorId);
//        }
//
//        @Override
//        public Result<LoadDataCollector<Long, String>> create(String source) {
//            lastResult = Result.<LoadDataCollector<Long, String>>builder()
//                    .success(true)
//                    .arg(id).build();
//            return null;
//        }
//
//        @Override
//        protected LoadDataCollector<Long, String> createCollector(String source) {
//            return null;
//        }
//    }
//
//    private static class CollectorCreatorForParsingFailImpl extends CollectorCreatorImpl<Long, String> {
//        public CollectorCreatorForParsingFailImpl(String creatorId) {
//            super(creatorId);
//        }
//
//        @Override
//        protected LoadDataCollector<Long, String> createCollector(String source) {
//            throw new JsonSyntaxException("");
//        }
//    }
//
//    private static class CollectorCreatorForSuccessImpl extends CollectorCreatorImpl<Long, String> {
//        public CollectorCreatorForSuccessImpl(String creatorId) {
//            super(creatorId);
//        }
//
//        @Override
//        protected LoadDataCollector<Long, String> createCollector(String source) {
//            return null;
//        }
//    }
//}