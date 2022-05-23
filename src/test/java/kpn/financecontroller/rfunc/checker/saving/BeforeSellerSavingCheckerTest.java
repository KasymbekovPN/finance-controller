package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeSellerSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Seller domain = new Builder().build();
        ImmutableResult<Seller> expectedResult = ImmutableResult.<Seller>fail("checking.domain.seller.name.isEmpty");

        Result<Seller> result = new BeforeSellerSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Seller domain = new Builder().name("").build();
        ImmutableResult<Seller> expectedResult = ImmutableResult.<Seller>fail("checking.domain.seller.name.isEmpty");

        Result<Seller> result = new BeforeSellerSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Seller domain = new Builder().name("name").build();
        ImmutableResult<Seller> expectedResult = ImmutableResult.<Seller>ok(domain);

        Result<Seller> result = new BeforeSellerSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder{
        private String name;

        public Seller build() {
            Seller seller = new Seller();
            seller.setName(name);
            return seller;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}