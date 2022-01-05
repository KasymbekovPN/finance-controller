package kpn.financecontroller.data.entities.currency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyEntityTest {

    @Test
    void shouldCheckIdSettingGetting() {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        long expectedId = 123L;
        currencyEntity.setId(expectedId);
        assertThat(expectedId).isEqualTo(currencyEntity.getId());
    }

    @Test
    void shouldCheckCodeGettingSetting() {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        String expectedCode = "RUB";
        currencyEntity.setCode(expectedCode);
        assertThat(expectedCode).isEqualTo(currencyEntity.getCode());
    }
}
