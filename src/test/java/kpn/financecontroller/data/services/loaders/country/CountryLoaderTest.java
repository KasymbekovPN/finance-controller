package kpn.financecontroller.data.services.loaders.country;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CountryLoaderTest {

    private static final Random RANDOM = new Random();

    private static long expectedId;
    private static CountryEntity expectedEntity;
    private static List<Country> expectedDomains;
    private static CountryLoader loader;

    @BeforeAll
    static void beforeAll() {
        expectedId = 1L;

        expectedEntity = new CountryEntity();
        expectedEntity.setId(expectedId);
        expectedEntity.setName("name");

        expectedDomains = List.of(new Country(expectedEntity));

        loader = new CountryLoader(createRepoMock());
    }

    @Test
    void shouldCheckFindingById() {
        Result<Country> result = loader.byId(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.loadById.unsupported");
        assertThat(result.getArgs()).isEqualTo(List.of("CountryLoader", expectedId).toArray());
    }

    @Test
    void shouldCheckFindingBy() {
        String attribute = String.format("attribute_%s", RANDOM.nextInt());
        String value = String.format("value_%s", RANDOM.nextInt());
        Result<List<Country>> result = loader.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getArgs()).isEqualTo(List.of("CountryLoader", attribute, value).toArray());
    }

    @Test
    void shouldCheckFindingAll() {
        Result<List<Country>> result = loader.all();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    private static CountryRepo createRepoMock() {
        CountryRepo repo = Mockito.mock(CountryRepo.class);

        Mockito
                .when(repo.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());
        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(expectedEntity));

        return repo;
    }
}