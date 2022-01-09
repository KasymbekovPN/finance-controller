package kpn.financecontroller.data.domains.currency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyTest {

    @Test
    void shouldCheckGetting() {
        String expectedCode = "RUB";
        long expectedId = 123L;
        Currency currency = new Currency(expectedId, expectedCode);
        assertThat(expectedId).isEqualTo(currency.getId());
        assertThat(expectedCode).isEqualTo(currency.getCode());
    }
}
