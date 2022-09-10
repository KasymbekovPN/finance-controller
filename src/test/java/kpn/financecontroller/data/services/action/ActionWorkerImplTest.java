package kpn.financecontroller.data.services.action;

import kpn.financecontroller.search.type.Searcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ActionWorkerImplTest {
    private static final String HEADER = """
            int x = 123;
            int y = 234;
            """;
    private static final String ALGORITHM = """
            Integer r = x + y;
            r
            """;

    @Test
    void shouldCheckExecution() {
        ActionWorkerImpl worker = new ActionWorkerImpl(HEADER);
        Object result = worker.execute(ALGORITHM);

        Assertions.assertThat(result).isEqualTo(123 + 234);
    }

    private TestSearcher createSearcher(){
        TestSearcher searcher = Mockito.mock(TestSearcher.class);
        Mockito
                .when(searcher.search(Mockito.anyString()))
                .thenReturn(HEADER);
        return searcher;
    }

    private abstract static class TestSearcher implements Searcher<String, String> {}
}