package kpn.financecontroller.data.services.loaders.city;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CityLoaderTest {

    private static final Random RANDOM = new Random();

    private static long expectedId;
    private static CityEntity expectedEntity;
    private static List<City> expectedDomains;
    private static CityLoader loader;

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

        expectedEntity = new CityEntity();
        expectedEntity.setId(123L);
        expectedEntity.setName("Moscow");
        expectedEntity.setRegionEntity(region);

        expectedDomains = List.of(new City(expectedEntity));

        loader = new CityLoader(createRepoMock());
    }

    @Test
    void shouldCheckFindingById() {
        Result<City> result = loader.byId(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.loadById.unsupported");
        assertThat(result.getArgs()).isEqualTo(List.of("CityLoader", expectedId).toArray());
    }

    @Test
    void shouldCheckFindingBy() {
        String attribute = String.format("attribute_%s", RANDOM.nextInt());
        String value = String.format("value_%s", RANDOM.nextInt());
        Result<List<City>> result = loader.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getArgs()).isEqualTo(List.of("CityLoader", attribute, value).toArray());
    }

    @Test
    void shouldCheckFindingAll() {
        Result<List<City>> result = loader.all();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    private static CityRepo createRepoMock() {
        CityRepo repo = Mockito.mock(CityRepo.class);

        Mockito
                .when(repo.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());
        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(expectedEntity));

        return repo;
    }
}