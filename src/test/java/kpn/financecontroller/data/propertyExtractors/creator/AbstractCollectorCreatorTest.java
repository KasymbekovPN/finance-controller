package kpn.financecontroller.data.propertyExtractors.creator;

import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractCollectorCreatorTest {

    private static final String CREATOR_ID = "id";

    private static Result<IECollector<Long, String>> expectedResultWhenCollectorResultNull;
    private static Result<IECollector<Long, String>> expectedResultWhenCheckGetting;
    private static Result<IECollector<Long, String>> expectedResultWhenParsingFail;
    private static Result<IECollector<Long, String>> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenCollectorResultNull = Result.<IECollector<Long, String>>builder()
                .success(false)
                .code("creator.collector.result.null")
                .arg(CREATOR_ID)
                .build();
        expectedResultWhenCheckGetting = Result.<IECollector<Long, String>>builder()
                .success(true)
                .arg(CREATOR_ID)
                .build();
        expectedResultWhenParsingFail = Result.<IECollector<Long, String>>builder()
                .success(false)
                .code("creator.collector.parsing.fail")
                .arg(CREATOR_ID)
                .build();
        expectedResult = Result.<IECollector<Long, String>>builder()
                .success(true)
                .code("creator.collector.parsing.success")
                .arg(CREATOR_ID)
                .build();
    }

    @Test
    void shouldCheckGettingWhenLastResultIsNull() {
        CollectorCreatorForNullResultGetting creator = new CollectorCreatorForNullResultGetting(CREATOR_ID);
        Result<IECollector<Long, String>> result = creator.get();
        assertThat(expectedResultWhenCollectorResultNull).isEqualTo(result);
    }

    @Test
    void shouldCheckGetting() {
        CollectorCreatorForGetting creator = new CollectorCreatorForGetting(CREATOR_ID);
        creator.create("");
        Result<IECollector<Long, String>> result = creator.get();
        assertThat(expectedResultWhenCheckGetting).isEqualTo(result);
    }

    @Test
    void shouldCheckCreationWhenParsingFail() {
        CollectorCreatorForParsingFail creator = new CollectorCreatorForParsingFail(CREATOR_ID);
        Result<IECollector<Long, String>> result = creator.create("");
        assertThat(expectedResultWhenParsingFail).isEqualTo(result);
    }

    @Test
    void shouldCheckCreation() {
        CollectorCreatorForSuccess creator = new CollectorCreatorForSuccess(CREATOR_ID);
        Result<IECollector<Long, String>> result = creator.create("");
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class CollectorCreatorForNullResultGetting extends AbstractCollectorCreator<Long, String> {
        public CollectorCreatorForNullResultGetting(String id) {
            super(id);
        }

        @Override
        protected IECollector<Long, String> createCollector(String source) {
            return null;
        }
    }

    private static class CollectorCreatorForGetting extends AbstractCollectorCreator<Long, String>{
        public CollectorCreatorForGetting(String creatorId) {
            super(creatorId);
        }

        @Override
        public Result<IECollector<Long, String>> create(String source) {
            lastResult = Result.<IECollector<Long, String>>builder()
                    .success(true)
                    .arg(id).build();
            return null;
        }

        @Override
        protected IECollector<Long, String> createCollector(String source) {
            return null;
        }
    }

    private static class CollectorCreatorForParsingFail extends AbstractCollectorCreator<Long, String>{
        public CollectorCreatorForParsingFail(String creatorId) {
            super(creatorId);
        }

        @Override
        protected IECollector<Long, String> createCollector(String source) {
            throw new JsonSyntaxException("");
        }
    }

    private static class CollectorCreatorForSuccess extends AbstractCollectorCreator<Long, String>{
        public CollectorCreatorForSuccess(String creatorId) {
            super(creatorId);
        }

        @Override
        protected IECollector<Long, String> createCollector(String source) {
            return null;
        }
    }
}