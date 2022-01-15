package kpn.financecontroller.data.services.loaders.street;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.repos.street.StreetRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class StreetLoaderTest {

    private static final Random RANDOM = new Random();

    private static long expectedId;
    private static StreetEntity expectedEntity;
    private static List<Street> expectedDomains;
    private static StreetLoader loader;

    @BeforeAll
    static void beforeAll() {
        expectedId = 1L;

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(1L);
        countryEntity.setName("Russia");

        RegionEntity region = new RegionEntity();
        region.setId(1L);
        region.setName("Moscow");
        region.setCountryEntity(countryEntity);

        CityEntity city = new CityEntity();
        city.setId(123L);
        city.setName("Moscow");
        city.setRegionEntity(region);

        expectedEntity = new StreetEntity();
        expectedEntity.setId(expectedId);
        expectedEntity.setName("some name");
        expectedEntity.setCityEntity(city);

        expectedDomains = List.of(new Street(expectedEntity));

        loader = new StreetLoader(createRepoMock());
    }

    @Test
    void shouldCheckFindingById() {
        Result<Street> result = loader.byId(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.loadById.unsupported");
        assertThat(result.getArgs()).isEqualTo(List.of("StreetLoader", expectedId).toArray());
    }

    @Test
    void shouldCheckFindingBy() {
        String attribute = String.format("attribute_%s", RANDOM.nextInt());
        String value = String.format("value_%s", RANDOM.nextInt());
        Result<List<Street>> result = loader.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getArgs()).isEqualTo(List.of("StreetLoader", attribute, value).toArray());
    }

    @Test
    void shouldCheckFindingAll() {
        Result<List<Street>> result = loader.all();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    private static StreetRepo createRepoMock() {
        StreetRepo repo = Mockito.mock(StreetRepo.class);

        Mockito
                .when(repo.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());
        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(expectedEntity));

        return repo;
    }
}