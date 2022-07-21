// TODO: 21.07.2022 restore
//package kpn.financecontroller.rfunc.checker.saving;
//
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.lib.result.ImmutableResult;
//import kpn.lib.result.Result;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class BeforeCountrySavingCheckerTest {
//
//    @Test
//    void shouldCheck_whenNameNull() {
//        Country domain = new Country();
//        ImmutableResult<Country> expectedResult = ImmutableResult.<Country>fail("checking.domain.country.name.isEmpty");
//
//        Result<Country> result = new BeforeCountrySavingChecker().apply(domain);
//        assertThat(expectedResult).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheck_whenNameEmpty() {
//        Country domain = new Country();
//        domain.setName("");
//        ImmutableResult<Country> expectedResult = ImmutableResult.<Country>fail("checking.domain.country.name.isEmpty");
//
//        Result<Country> result = new BeforeCountrySavingChecker().apply(domain);
//        assertThat(expectedResult).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheck() {
//        Country domain = new Country();
//        domain.setName("some.name");
//        ImmutableResult<Country> expectedResult = ImmutableResult.<Country>ok(domain);
//
//        Result<Country> result = new BeforeCountrySavingChecker().apply(domain);
//        assertThat(expectedResult).isEqualTo(result);
//    }
//}