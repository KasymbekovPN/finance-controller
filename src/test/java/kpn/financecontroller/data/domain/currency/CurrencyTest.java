package kpn.financecontroller.data.domain.currency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyTest {

    @Test
    void shouldCheckGetting() {
        String expectedValue = "RUB";
        Currency currency = new Currency(expectedValue);
        assertThat(expectedValue).isEqualTo(currency.getCode());
    }
}
