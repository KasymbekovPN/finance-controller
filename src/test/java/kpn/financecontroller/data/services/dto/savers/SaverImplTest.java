package kpn.financecontroller.data.services.dto.savers;

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

class SaverImplTest {

    private static final String SAVER_NAME = "saver";

    private static TestEntity throwEntity;
    private static TestEntity testEntity;
    private static TestModel testModel;
    private static SaverImpl<TestModel, TestEntity, Long> saver;

    @BeforeAll
    static void beforeAll() {
        throwEntity = new TestEntity();
        throwEntity.setId(-1L);

        testEntity = new TestEntity();
        testEntity.setId(1L);

        testModel = new TestModel(testEntity);

        saver = new SaverImpl<>(createRepo(), TestModel::new, SAVER_NAME);
    }

    @Test
    void shouldCheckSaving() {
        Result<TestModel> result = saver.save(testEntity);
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(testModel);
    }

    @Test
    void shouldCheckWrongWaySaving() {
        Result<TestModel> result = saver.save(throwEntity);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("saver.saveImpl.fail");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(SAVER_NAME).toArray());
    }

    private static JpaRepository<TestEntity, Long> createRepo(){
        TestRepo repo = Mockito.mock(TestRepo.class);

        Mockito
                .when(repo.save(throwEntity))
                .thenThrow(new MockitoException(""));

        Mockito
                .when(repo.save(testEntity))
                .thenReturn(testEntity);

        return repo;
    }
}