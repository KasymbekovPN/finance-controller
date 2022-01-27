package kpn.financecontroller.data.services.deleters;

import kpn.financecontroller.data.services.utils.TestEntity;
import kpn.financecontroller.data.services.utils.TestModel;
import kpn.financecontroller.data.services.utils.TestRepo;
import kpn.financecontroller.result.Result;
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
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.deleteById.fail");
        assertThat(result.getArgs()).isEqualTo(List.of(DELETER_NAME, expectedId).toArray());
    }

    @Test
    void shouldCheckBy() {
        String attribute = "attribute";
        String value = "value";
        Result<Void> result = deleter.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.by.attribute.disallowed");
        assertThat(result.getArgs()).isEqualTo(List.of(DELETER_NAME, attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<Void> result = deleter.all();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.deleteAll.fail");
        assertThat(result.getArgs()).isEqualTo(List.of(DELETER_NAME).toArray());
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