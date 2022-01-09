package kpn.financecontroller.data.savers.currency;

import kpn.financecontroller.data.domains.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repos.currency.CurrencyRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencySaverTest {

    private static CurrencyRepo repo;
    private static CurrencyEntity throwableEntity;
    private static CurrencyEntity resultEntity;
    private static CurrencySaver saver;

    @BeforeAll
    static void beforeAll() {
        throwableEntity = new CurrencyEntity();
        throwableEntity.setId(-1L);

        resultEntity = new CurrencyEntity();
        resultEntity.setId(1L);
        resultEntity.setCode("code");

        repo = createRepoMock();

        saver = new CurrencySaver(repo);
    }

    @Test
    void shouldCheckSaving() {
        Result<Currency> result = saver.save(new CurrencyEntity());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getId()).isEqualTo(resultEntity.getId());
        assertThat(result.getValue().getCode()).isEqualTo(resultEntity.getCode());
    }

    @Test
    void shouldCheckThrowableSaving() {
        Result<Currency> result = saver.save(throwableEntity);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.save.fail");
        assertThat(result.getArgs()).isEqualTo(List.of("CurrencySaver").toArray());
    }

    private static CurrencyRepo createRepoMock() {
        CurrencyRepo repo = Mockito.mock(CurrencyRepo.class);
        Mockito
                .when(repo.save(Mockito.any(CurrencyEntity.class)))
                .thenReturn(resultEntity);
        Mockito
                .when(repo.save(throwableEntity))
                .thenThrow(new MockitoException(""));
        return repo;
    }
}