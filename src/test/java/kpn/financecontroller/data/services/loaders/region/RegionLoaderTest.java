package kpn.financecontroller.data.services.loaders.region;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class RegionLoaderTest {

    private static final Random RANDOM = new Random();

    private static long expectedId;
    private static RegionEntity expectedEntity;
    private static List<Region> expectedDomains;
    private static RegionLoader loader;

    @BeforeAll
    static void beforeAll() {
        expectedId = 1L;

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(1L);
        countryEntity.setName("Russia");

        expectedEntity = new RegionEntity();
        expectedEntity.setId(expectedId);
        expectedEntity.setName("Moscow");
        expectedEntity.setCountryEntity(countryEntity);

        expectedDomains = List.of(new Region(expectedEntity));

        loader = new RegionLoader(createRepoMock());
    }

    @Test
    void shouldCheckFindingById() {
        Result<Region> result = loader.byId(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.loadById.unsupported");
        assertThat(result.getArgs()).isEqualTo(List.of("RegionLoader", expectedId).toArray());
    }

    @Test
    void shouldCheckFindingBy() {
        String attribute = String.format("attribute_%s", RANDOM.nextInt());
        String value = String.format("value_%s", RANDOM.nextInt());
        Result<List<Region>> result = loader.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getArgs()).isEqualTo(List.of("RegionLoader", attribute, value).toArray());
    }

    @Test
    void shouldCheckFindingAll() {
        Result<List<Region>> result = loader.all();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    private static RegionRepo createRepoMock() {
        RegionRepo repo = Mockito.mock(RegionRepo.class);

        Mockito
                .when(repo.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());
        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(expectedEntity));

        return repo;
    }
}