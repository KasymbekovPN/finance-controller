package kpn.financecontroller.search.type;

import kpn.financecontroller.annotation.External;
import kpn.financecontroller.search.type.pack.TestClass;
import kpn.financecontroller.search.type.pack.inner.InnerTestClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class DeepClassSearcherByExternalAnnotationTest {

    @Test
    void shouldMarkedClassLoading() {
        String packageName = "kpn/financecontroller/search/type";

        DeepClassSearcherByExternalAnnotation searcher = new DeepClassSearcherByExternalAnnotation(External.class);
        Set<Class<?>> result = searcher.search(packageName);
        System.out.println(result);

        HashSet<Object> expectedResult = new HashSet<>();
        expectedResult.add(TestClass.class);
        expectedResult.add(InnerTestClass.class);
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }
}