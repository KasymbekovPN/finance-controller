package kpn.financecontroller.data.propertyExtractors.fillers;

import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractCollectorFillerTest {

    private static final String FAIL_FILLER_NAME = "wrong";
    private static final String SUCCESS_FILLER_NAME = "right";

    private static Result<Void> expectedWhenFail;
    private static Result<Void> expectedWhenSuccess;

    @BeforeAll
    static void beforeAll() {
        expectedWhenFail = Result.<Void>builder()
                .success(false)
                .code("json.parsing.fail")
                .arg(FAIL_FILLER_NAME)
                .build();
        expectedWhenSuccess = Result.<Void>builder()
                .success(true)
                .code("json.parsing.success")
                .arg(SUCCESS_FILLER_NAME)
                .build();
    }

    @Test
    void shouldCheckFailFilling() {
        FailTestFiller filler = new FailTestFiller(FAIL_FILLER_NAME);
        Result<Void> result = filler.fill("");
        assertThat(expectedWhenFail).isEqualTo(result);
    }

    private static class FailTestFiller extends AbstractCollectorFiller<Long, String>{

        public FailTestFiller(String name) {
            super(name);
        }

        @Override
        protected IECollector<Long, String> createCollector(String jsonContent) {
            throw new JsonSyntaxException("");
        }
    }

    @Test
    void shouldCheckSuccessFilling() {
        SuccessTestFiller filler = new SuccessTestFiller(SUCCESS_FILLER_NAME);
        Result<Void> result = filler.fill("");
        assertThat(expectedWhenSuccess).isEqualTo(result);
    }

    private static class SuccessTestFiller extends AbstractCollectorFiller<Long, String>{

        public SuccessTestFiller(String name) {
            super(name);
        }

        @Override
        protected IECollector<Long, String> createCollector(String jsonContent) {
            return null;
        }
    }
}