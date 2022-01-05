package kpn.financecontroller.data.service.currency;

import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repo.currency.CurrencyRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CurrencyServiceTest {

    private static final String FIRST_CODE = "RUB";
    private static final String SECOND_CODE = "USD";

    private final CurrencyService currencyService;
    private final CurrencyRepo currencyRepo;

    private Long expectedId;

    @Autowired
    public CurrencyServiceTest(CurrencyService currencyService, CurrencyRepo currencyRepo) {
        this.currencyService = currencyService;
        this.currencyRepo = currencyRepo;
    }

    @BeforeEach
    void setUp() {
        expectedId = currencyRepo.save(createCurrencyEntity(FIRST_CODE)).getId();
    }

    @Test
    void shouldCheckSaving() {
        Result<Currency> result = currencyService.save(createCurrencyEntity(SECOND_CODE));
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckFailSavingAttempt() {
        Result<Currency> result = currencyService.save(createCurrencyEntity(FIRST_CODE));
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.currency.save.fail");
    }

    @Test
    void shouldCheckLoadingById() {
        Result<Currency> result = currencyService.loadById(expectedId);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getCode()).isEqualTo(FIRST_CODE);
    }

    @Test
    void shouldCheckFailLoadingAttemptById() {
        long expectedId = -1L;
        Result<Currency> result = currencyService.loadById(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.currency.loadById.noOne");
        assertThat(result.getArgs()).isEqualTo(List.of(expectedId));
    }

    @Test
    void shouldCheckDeleting() {
        currencyService.deleteById(expectedId);
        Result<Currency> result = currencyService.loadById(expectedId);
        assertThat(result.getSuccess()).isFalse();
    }

    @AfterEach
    void tearDown() {
        currencyRepo.deleteAll();
    }

    private CurrencyEntity createCurrencyEntity(String code) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setCode(code);
        return currencyEntity;
    }
}
