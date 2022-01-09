package kpn.financecontroller.data.service.currency;

import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repo.currency.CurrencyRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyLoaderTest {

    private static final Random RANDOM = new Random();

    private static CurrencyLoader loader;
    private static long expectedId;
    private static long unexpectedId;
    private static CurrencyEntity expectedEntity;
    private static List<Currency> expectedDomains;

    @BeforeAll
    static void beforeAll() {
        expectedId = 1L;
        unexpectedId = 2L;

        expectedEntity = new CurrencyEntity();
        expectedEntity.setId(expectedId);
        expectedEntity.setCode("code");

        expectedDomains = List.of(new Currency(expectedEntity));

        CurrencyRepo repo = createRepoMock();
        loader = new CurrencyLoader(repo);
    }

    @Test
    void shouldCheckFindingById() {
        Result<Currency> result = loader.byId(expectedId);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getId()).isEqualTo(expectedEntity.getId());
        assertThat(result.getValue().getCode()).isEqualTo(expectedEntity.getCode());
    }

    @Test
    void shouldCheckWrongFindingById() {
        Result<Currency> result = loader.byId(unexpectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.byId.noOne");
        assertThat(result.getArgs()).isEqualTo(List.of("CurrencyLoader", unexpectedId).toArray());
    }

    @RepeatedTest(100)
    void shouldCheckFindingBy() {
        String attribute = String.format("attribute_%s", RANDOM.nextInt());
        String value = String.format("value_%s", RANDOM.nextInt());
        Result<List<Currency>> result = loader.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getArgs()).isEqualTo(List.of("CurrencyLoader", attribute).toArray());
    }

    @Test
    void shouldCheckFindingAll() {
        Result<List<Currency>> result = loader.all();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    private static CurrencyRepo createRepoMock() {
        CurrencyRepo repo = Mockito.mock(CurrencyRepo.class);

        Mockito
                .when(repo.findById(expectedId))
                .thenReturn(Optional.of(expectedEntity));
        Mockito
                .when(repo.findById(unexpectedId))
                .thenReturn(Optional.empty());
        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(expectedEntity));

        return repo;
    }
}