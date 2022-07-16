package kpn.financecontroller.data.converters.aspect;

import kpn.lib.aspect.DefaultAspectResult;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import support.TestDomain;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FromAspectConverterTest {

    @Test
    void shouldCheckConversion_success() {
        TestDomain domain = new TestDomain();
        domain.setId(1L);
        domain.setName("name");

        DefaultAspectResult<TestDomain> aspectResult = new DefaultAspectResult<>(new DefaultExecutorResult<>(domain));

        Result<List<TestDomain>> result = new FromAspectConverter<TestDomain>().apply(aspectResult);
        assertThat(result).isEqualTo(ImmutableResult.<List<TestDomain>>ok(List.of(domain)));
    }

    @Test
    void shouldCheckConversion_fail() {
        String code = "code";
        String[] args = {"arg0", "arg1"};

        DefaultAspectResult<TestDomain> aspectResult = new DefaultAspectResult<>(code, args);
        Result<List<TestDomain>> result = new FromAspectConverter<TestDomain>().apply(aspectResult);

        ImmutableResult.Builder<List<TestDomain>> builder = ImmutableResult.<List<TestDomain>>builder()
                .code(code);
        Arrays.stream(args).forEach(builder::arg);
        assertThat(result).isEqualTo(builder.build());
    }
}