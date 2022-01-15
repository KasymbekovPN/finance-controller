package kpn.financecontroller.data.services.savers.street;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.repos.street.StreetRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StreetSaverTest {

    private static final Long EXPECTED_ID = 123L;

    private static StreetEntity throwableEntity;
    private static StreetEntity resultEntity;
    private static StreetSaver saver;

    @BeforeAll
    static void beforeAll() {

        throwableEntity = new StreetEntity();
        throwableEntity.setId(-1L);

        resultEntity = new StreetEntity();
        resultEntity.setId(EXPECTED_ID);

        saver = new StreetSaver(createRepoMock());
    }

    @Test
    void shouldCheckSaving() {
        Result<Street> result = saver.save(new StreetEntity());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getId()).isEqualTo(resultEntity.getId());
    }

    @Test
    void shouldCheckThrowableSaving() {
        Result<Street> result = saver.save(throwableEntity);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("saver.saveImpl.fail");
        assertThat(result.getArgs()).isEqualTo(List.of("StreetSaver").toArray());
    }

    private static StreetRepo createRepoMock() {
        StreetRepo repo = Mockito.mock(StreetRepo.class);
        Mockito
                .when(repo.save(Mockito.any(StreetEntity.class)))
                .thenReturn(resultEntity);
        Mockito
                .when(repo.save(throwableEntity))
                .thenThrow(new MockitoException(""));
        return repo;
    }
}