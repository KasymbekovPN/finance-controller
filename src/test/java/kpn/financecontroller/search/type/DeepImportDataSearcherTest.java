package kpn.financecontroller.search.type;

import kpn.financecontroller.search.type.pack.TestClass;
import kpn.financecontroller.search.type.pack.inner.InnerTestClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeepImportDataSearcherTest {
    private static final List<Class<?>> CLASS_SEARCHER_RESULT = List.of(
            DeepImportDataSearcherTest.class,
            TestClass.class,
            InnerTestClass.class
    );

    @Test
    void shouldCheckImportDataSearching() {
        StringBuilder expectedResult = new StringBuilder();
        for (Class<?> aClass : CLASS_SEARCHER_RESULT) {
            expectedResult
                    .append("import ")
                    .append(aClass.getPackage().getName())
                    .append(".")
                    .append(aClass.getSimpleName())
                    .append(";\n");
        }

        DeepImportDataSearcher searcher = new DeepImportDataSearcher(createClassSearcher());
        String result = searcher.search("");

        assertThat(result).isEqualTo(expectedResult.toString());
    }

    private TestClassSearcher createClassSearcher(){
        TestClassSearcher searcher = Mockito.mock(TestClassSearcher.class);
        Mockito
                .when(searcher.search(Mockito.anyString()))
                .thenReturn(CLASS_SEARCHER_RESULT);
        return searcher;
    }

    private abstract static class TestClassSearcher implements Searcher<String, List<Class<?>>>{}
}