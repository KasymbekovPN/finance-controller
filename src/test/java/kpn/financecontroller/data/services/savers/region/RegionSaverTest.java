package kpn.financecontroller.data.services.savers.region;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegionSaverTest {

    private static RegionEntity throwableEntity;
    private static RegionEntity resultEntity;
    private static RegionSaver saver;
    private static Country expectedCountry;

    @BeforeAll
    static void beforeAll() {
        throwableEntity = new RegionEntity();
        throwableEntity.setId(-1L);

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(1L);
        countryEntity.setName("Russia");

        resultEntity = new RegionEntity();
        resultEntity.setId(1L);
        resultEntity.setName("Moscow");
        resultEntity.setCountryEntity(countryEntity);

        expectedCountry = new Country(countryEntity);

        saver = new RegionSaver(createRepoMock());
    }

    @Test
    void shouldCheckSaving() {
        Result<Region> result = saver.save(new RegionEntity());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getId()).isEqualTo(resultEntity.getId());
        assertThat(result.getValue().getName()).isEqualTo(resultEntity.getName());
        assertThat(result.getValue().getCountry()).isEqualTo(expectedCountry);
    }

    @Test
    void shouldCheckThrowableSaving() {
        Result<Region> result = saver.save(throwableEntity);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("saver.saveImpl.fail");
        assertThat(result.getArgs()).isEqualTo(List.of("RegionSaver").toArray());
    }

    private static RegionRepo createRepoMock() {
        RegionRepo repo = Mockito.mock(RegionRepo.class);
        Mockito
                .when(repo.save(Mockito.any(RegionEntity.class)))
                .thenReturn(resultEntity);
        Mockito
                .when(repo.save(throwableEntity))
                .thenThrow(new MockitoException(""));
        return repo;
    }
}