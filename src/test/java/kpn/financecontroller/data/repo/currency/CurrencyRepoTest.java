package kpn.financecontroller.data.repo.currency;

import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
public class CurrencyRepoTest {

    private static final String FIRST_CODE = "RUB";
    private static final String SECOND_CODE = "USD";

    private final CurrencyRepo currencyRepo;

    private Long expectedId;


    @Autowired
    public CurrencyRepoTest(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @BeforeEach
    void setUp() {
        expectedId = currencyRepo.save(createCurrencyEntity(FIRST_CODE)).getId();
    }

    @Test
    void shouldCheckSaving() {
        CurrencyEntity savedEntity = currencyRepo.save(createCurrencyEntity(SECOND_CODE));
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
    }

    @Test
    void shouldCheckFindingById() {
        Optional<CurrencyEntity> maybeCurrentEntity = currencyRepo.findById(expectedId);
        assertThat(maybeCurrentEntity).isPresent();
        CurrencyEntity currencyEntity = maybeCurrentEntity.get();
        assertThat(FIRST_CODE).isEqualTo(currencyEntity.getCode());
    }

    @Test
    void shouldCheckFindingByCode() {
        List<CurrencyEntity> currencyEntities = currencyRepo.findByCode(FIRST_CODE);
        assertThat(currencyEntities.size()).isEqualTo(1);
        CurrencyEntity currencyEntity = currencyEntities.get(0);
        assertThat(FIRST_CODE).isEqualTo(currencyEntity.getCode());
    }

    @Test
    void shouldCheckDuplicateCodeInsertionAttempt() {
        Throwable throwable = catchThrowable(() -> {
            currencyRepo.save(createCurrencyEntity(FIRST_CODE));
        });
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldCheckDeleting() {
        currencyRepo.deleteById(expectedId);
        Optional<CurrencyEntity> maybeCurrencyEntity = currencyRepo.findById(expectedId);
        assertThat(maybeCurrencyEntity).isEmpty();
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
