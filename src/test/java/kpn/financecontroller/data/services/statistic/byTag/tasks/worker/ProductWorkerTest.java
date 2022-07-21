package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converters.aspect.FromAspectConverter;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.product.QProductEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.loading.CompletelyLoadingExecutor;
import kpn.lib.executor.predicate.PredicateExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ProductWorkerTest {

    private static ImmutableResult<List<Product>> executorResult;
    private static ImmutableResult<List<Product>> loaderResult;

    @BeforeAll
    static void beforeAll() {
        executorResult = ImmutableResult.<List<Product>>ok(createProducts(1L, 2L, 3L));
        loaderResult = ImmutableResult.<List<Product>>ok(createProducts(1L, 2L, 3L, 4L, 5L));
    }

    private static List<Product> createProducts(Long... ids) {
        return Arrays.stream(ids).map(id -> {
            Product product = new Product();
            product.setId(id);
            return product;
        }).collect(Collectors.toList());
    }

    @Test
    void shouldCheckFinding_byTags() throws DTOException {
        ProductTask task = createTask(false, 1L, 2L);

        ProductWorker worker = new ProductWorker(createService(), createConverter());
        Result<List<Product>> result = worker.execute(task);

        assertThat(executorResult).isEqualTo(result);
    }

    @Test
    void shouldCheckFinding_forAll() throws DTOException {
        ProductTask task = createTask(true, 1L, 2L);

        ProductWorker worker = new ProductWorker(createService(), createConverter());
        Result<List<Product>> result = worker.execute(task);

        assertThat(loaderResult).isEqualTo(result);
    }

    private ProductTask createTask(boolean allTags, Long... tagIds) {
        ProductTask task = new ProductTask();
        task.setAllTags(allTags);
        task.setTags(
                Arrays.stream(tagIds).map(id -> {
                    Tag tag = new Tag();
                    tag.setId(id);
                    tag.setName("tag" + id);
                    return tag;
                }).collect(Collectors.toSet())
        );
        return task;
    }

    private Service<Long, Product, Predicate, Result<List<Product>>> createService() throws DTOException {
        return new ServiceBuider<Long, Product, Predicate, Result<List<Product>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(createExecutor())
                .and()
                .loadingAspectBuilder()
                .executorAll(createLoader())
                .and()
                .build();
    }

    private Function<ProductTask, Predicate> createConverter() {
        TestConverter converter = Mockito.mock(TestConverter.class);
        Mockito
                .when(converter.apply(Mockito.anyObject()))
                .thenReturn(QProductEntity.productEntity.tagEntities.contains(new TagEntity()));
        return converter;
    }

    private TestExecutor createExecutor() throws DTOException {
        TestExecutor executor = Mockito.mock(TestExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(new DefaultExecutorResult<>(executorResult.getValue()));
        return executor;
    }

    private TestLoader createLoader() throws DTOException {
        TestLoader loader = Mockito.mock(TestLoader.class);
        Mockito
                .when(loader.load())
                .thenReturn(new DefaultExecutorResult<>(loaderResult.getValue()));
        return loader;
    }

    abstract private static class TestExecutor implements PredicateExecutor<Predicate, Product> {}
    abstract private static class TestLoader implements CompletelyLoadingExecutor<Product> {}
    abstract private static class TestConverter implements Function<ProductTask, Predicate>{}
}