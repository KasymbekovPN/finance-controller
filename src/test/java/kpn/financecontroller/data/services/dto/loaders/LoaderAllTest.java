package kpn.financecontroller.data.services.dto.loaders;

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
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LoaderAllTest {

    private static final String LOADER_NAME = "loader";
    private static final String WRONG_LOADER_NAME = "wrong.loader";

    private static LoaderAll<TestModel, TestEntity, Long> loader;
    private static LoaderAll<TestModel, TestEntity, Long> wrongLoader;
    private static List<TestModel> expectedDomains;
    private static TestEntity inputEntity;

    @BeforeAll
    static void beforeAll() {

        inputEntity = new TestEntity();
        inputEntity.setId(1L);
        inputEntity.setValue("value");
        expectedDomains = List.of(new TestModel(inputEntity));

        Function<List<TestEntity>, List<TestModel>> toDomains = (entities) -> {
            return entities.stream().map(TestModel::new).collect(Collectors.toList());
        };
        loader = new LoaderAll<>(createRepo(), LOADER_NAME, TestModel::new, toDomains);
        wrongLoader = new LoaderAll<>(createWrongRepo(), WRONG_LOADER_NAME, TestModel::new, toDomains);
    }

    @Test
    void shouldCheckById() {
        long expectedId = 1L;
        Result<TestModel> result = loader.byId(expectedId);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("loader.loadById.unsupported");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(LOADER_NAME, expectedId).toArray());
    }

    @Test
    void shouldCheckBy() {
        String attribute = "attribute";
        String value = "value";
        Result<List<TestModel>> result = loader.by(attribute, value);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(LOADER_NAME, attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<List<TestModel>> result = loader.all();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    @Test
    void shouldCheckWrongAll() {
        Result<List<TestModel>> result = wrongLoader.all();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("loader.loadAll.fail");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(WRONG_LOADER_NAME).toArray());
    }

    private static JpaRepository<TestEntity, Long> createRepo() {
        TestRepo repo = Mockito.mock(TestRepo.class);

        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(inputEntity));
        return repo;
    }

    private static JpaRepository<TestEntity, Long> createWrongRepo() {
        TestRepo repo = Mockito.mock(TestRepo.class);

        Mockito
                .when(repo.findAll())
                .thenThrow(new MockitoException(""));
        return repo;
    }
}