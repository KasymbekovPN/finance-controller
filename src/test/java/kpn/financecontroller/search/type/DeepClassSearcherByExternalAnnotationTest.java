package kpn.financecontroller.search.type;

import kpn.financecontroller.annotation.External;
import kpn.financecontroller.search.type.pack.TestClass;
import kpn.financecontroller.search.type.pack.inner.InnerTestClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DeepClassSearcherByExternalAnnotationTest {

    @Test
    void shouldCheckMarkedClassLoading() {
        String packageName = "kpn/financecontroller/search/type";

        DeepClassSearcherByExternalAnnotation searcher = new DeepClassSearcherByExternalAnnotation(External.class);
        List<Class<?>> result = searcher.search(packageName);
        System.out.println(result);

        List<Object> expectedResult = new ArrayList<>();
        expectedResult.add(TestClass.class);
        expectedResult.add(InnerTestClass.class);
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }
}