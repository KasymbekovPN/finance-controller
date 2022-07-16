package kpn.financecontroller.data.services.dto.loaders;

import kpn.financecontroller.data.services.dto.utils.TestEntityOld;
import kpn.financecontroller.data.services.dto.utils.TestModelOld;
import kpn.financecontroller.data.services.dto.utils.TestRepoOld;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LoaderOldAllAndByIdTest {

    private static final String LOADER_NAME = "loader";
    private static final String WRONG_LOADER_NAME = "wrong.loader";

    private static LoaderOldAllAndById<TestModelOld, TestEntityOld, Long> loader;
    private static LoaderOldAllAndById<TestModelOld, TestEntityOld, Long> wrongLoader;
    private static List<TestModelOld> expectedDomains;
    private static TestEntityOld inputEntity;
    private static TestModelOld expectedDomain;

    @BeforeAll
    static void beforeAll() {

        inputEntity = new TestEntityOld();
        inputEntity.setId(1L);
        inputEntity.setValue("value");
        expectedDomains = List.of(new TestModelOld(inputEntity));
        expectedDomain = new TestModelOld(inputEntity);

        Function<List<TestEntityOld>, List<TestModelOld>> toDomains = (entities) -> {
            return entities.stream().map(TestModelOld::new).collect(Collectors.toList());
        };
        loader = new LoaderOldAllAndById<>(createRepo(), LOADER_NAME, TestModelOld::new, toDomains);
        wrongLoader = new LoaderOldAllAndById<>(createWrongRepo(), WRONG_LOADER_NAME, TestModelOld::new, toDomains);
    }

    @Test
    void shouldCheckById() {
        long expectedId = 1L;
        Result<TestModelOld> result = loader.byId(expectedId);
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomain);
    }

    @Test
    void shouldCheckWrongById() {
        long expectedId = 1L;
        Result<TestModelOld> result = wrongLoader.byId(expectedId);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("loader.loadById.fail");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(WRONG_LOADER_NAME, expectedId).toArray());
    }

    @Test
    void shouldCheckBy() {
        String attribute = "attribute";
        String value = "value";
        Result<List<TestModelOld>> result = loader.by(attribute, value);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("loader.by.disallowedAttribute");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(LOADER_NAME, attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<List<TestModelOld>> result = loader.all();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(expectedDomains);
    }

    @Test
    void shouldCheckWrongAll() {
        Result<List<TestModelOld>> result = wrongLoader.all();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getSeed().getCode()).isEqualTo("loader.loadAll.fail");
        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(WRONG_LOADER_NAME).toArray());
    }

    private static JpaRepository<TestEntityOld, Long> createRepo() {
        TestRepoOld repo = Mockito.mock(TestRepoOld.class);

        Mockito
                .when(repo.findAll())
                .thenReturn(List.of(inputEntity));
        Mockito
                .when(repo.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(inputEntity));
        return repo;
    }

    private static JpaRepository<TestEntityOld, Long> createWrongRepo() {
        TestRepoOld repo = Mockito.mock(TestRepoOld.class);

        Mockito
                .when(repo.findAll())
                .thenThrow(new MockitoException(""));
        Mockito
                .when(repo.findById(Mockito.any(Long.class)))
                .thenThrow(new MockitoException(""));
        return repo;
    }
}