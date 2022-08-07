package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Country;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeCountrySavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Country domain = new Country();
        ImmutableResult<List<Country>> expectedResult = ImmutableResult.<List<Country>>fail("checking.domain.country.name.isEmpty");

        Result<List<Country>> result = new BeforeCountrySavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Country domain = new Country();
        domain.setName("");
        ImmutableResult<List<Country>> expectedResult = ImmutableResult.<List<Country>>fail("checking.domain.country.name.isEmpty");

        Result<List<Country>> result = new BeforeCountrySavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Country domain = new Country();
        domain.setName("some.name");
        ImmutableResult<List<Country>> expectedResult = ImmutableResult.<List<Country>>ok(List.of(domain));

        Result<List<Country>> result = new BeforeCountrySavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }
}