package kpn.financecontroller.data.services.dto.deleters;

import kpn.financecontroller.data.services.dto.utils.TestEntity;
import kpn.financecontroller.data.services.dto.utils.TestModel;
import kpn.financecontroller.data.services.dto.utils.TestRepo;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeleterAllAndByIdTest {

    private static final String DELETER_NAME = "some.deleter";

    private static DeleterAllAndById<TestModel, TestEntity, Long> deleter;

    @BeforeAll
    static void beforeAll() {
        deleter = new DeleterAllAndById<>(createRepo(), DELETER_NAME);
    }

    @Test
    void shouldCheckById() {
        long expectedId = 1L;
        Result<Void> result = deleter.byId(expectedId);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("deleter.deleteById.fail");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(DELETER_NAME, expectedId).toArray());
    }

    @Test
    void shouldCheckBy() {
        String attribute = "attribute";
        String value = "value";
        Result<Void> result = deleter.by(attribute, value);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("deleter.by.attribute.disallowed");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(DELETER_NAME, attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<Void> result = deleter.all();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("deleter.deleteAll.fail");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(DELETER_NAME).toArray());
    }

    private static JpaRepository<TestEntity, Long> createRepo(){
        TestRepo repo = Mockito.mock(TestRepo.class);

        Mockito
                .doThrow(MockitoException.class)
                .when(repo)
                .deleteById(Mockito.any(Long.class));

        Mockito
                .doThrow(MockitoException.class)
                .when(repo)
                .deleteAll();

        return repo;
    }
}