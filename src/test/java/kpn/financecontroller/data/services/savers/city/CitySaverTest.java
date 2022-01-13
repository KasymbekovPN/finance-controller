package kpn.financecontroller.data.services.savers.city;


import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CitySaverTest {

    private static final Long EXPECTED_ID = 123L;

    private static CityEntity throwableEntity;
    private static CityEntity resultEntity;
    private static CitySaver saver;

    @BeforeAll
    static void beforeAll() {

        throwableEntity = new CityEntity();
        throwableEntity.setId(-1L);

        resultEntity = new CityEntity();
        resultEntity.setId(EXPECTED_ID);

        saver = new CitySaver(createRepoMock());
    }

    @Test
    void shouldCheckSaving() {
        Result<City> result = saver.save(new CityEntity());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getId()).isEqualTo(resultEntity.getId());
    }

    @Test
    void shouldCheckThrowableSaving() {
        Result<City> result = saver.save(throwableEntity);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("saver.saveImpl.fail");
        assertThat(result.getArgs()).isEqualTo(List.of("CitySaver").toArray());
    }

    private static CityRepo createRepoMock() {
        CityRepo repo = Mockito.mock(CityRepo.class);
        Mockito
                .when(repo.save(Mockito.any(CityEntity.class)))
                .thenReturn(resultEntity);
        Mockito
                .when(repo.save(throwableEntity))
                .thenThrow(new MockitoException(""));
        return repo;
    }
}