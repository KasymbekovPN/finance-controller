package kpn.financecontroller.data.services.savers.country;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CountrySaverTest {
    private static CountryEntity throwableEntity;
    private static CountryEntity resultEntity;
    private static CountrySaver saver;

    @BeforeAll
    static void beforeAll() {
        throwableEntity = new CountryEntity();
        throwableEntity.setId(-1L);

        resultEntity = new CountryEntity();
        resultEntity.setId(1L);
        resultEntity.setName("name");

        saver = new CountrySaver(createRepoMock());
    }

    @Test
    void shouldCheckSaving() {
        Result<Country> result = saver.save(new CountryEntity());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getId()).isEqualTo(resultEntity.getId());
        assertThat(result.getValue().getName()).isEqualTo(resultEntity.getName());
    }

    @Test
    void shouldCheckThrowableSaving() {
        Result<Country> result = saver.save(throwableEntity);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("saver.saveImpl.fail");
        assertThat(result.getArgs()).isEqualTo(List.of("CountrySaver").toArray());
    }

    private static CountryRepo createRepoMock() {
        CountryRepo repo = Mockito.mock(CountryRepo.class);
        Mockito
                .when(repo.save(Mockito.any(CountryEntity.class)))
                .thenReturn(resultEntity);
        Mockito
                .when(repo.save(throwableEntity))
                .thenThrow(new MockitoException(""));
        return repo;
    }
}