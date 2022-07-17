// TODO: 17.07.2022 restore
//package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;
//
//import com.querydsl.core.types.Predicate;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.data.entities.product.ProductEntity;
//import kpn.financecontroller.data.entities.product.QProductEntity;
//import kpn.financecontroller.data.entities.tag.TagEntity;
//import kpn.financecontroller.data.services.dto.DTOServiceOLdOld;
//import kpn.financecontroller.data.services.dto.DTOServiceOLdImplOld;
//import kpn.financecontroller.data.services.dto.executors.PredicateExecutorOld;
//import kpn.financecontroller.data.services.dto.loaders.LoaderOld;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
//import kpn.lib.result.ImmutableResult;
//import kpn.lib.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ProductWorkerTest {
//
//    private static ImmutableResult<List<Product>> executorResult;
//    private static ImmutableResult<List<Product>> loaderResult;
//
//    @BeforeAll
//    static void beforeAll() {
//        executorResult = ImmutableResult.<List<Product>>ok(createProducts(1L, 2L, 3L));
//        loaderResult = ImmutableResult.<List<Product>>ok(createProducts(1L, 2L, 3L, 4L, 5L));
//    }
//
//    private static List<Product> createProducts(Long... ids) {
//        return Arrays.stream(ids).map(id -> {
//            Product product = new Product();
//            product.setId(id);
//            return product;
//        }).collect(Collectors.toList());
//    }
//
//    @Test
//    void shouldCheckFinding_byTags() {
//        ProductTask task = createTask(false, 1L, 2L);
//
//        ProductWorker worker = new ProductWorker(createService(), createConverter());
//        Result<List<Product>> result = worker.execute(task);
//
//        assertThat(executorResult).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheckFinding_forAll() {
//        ProductTask task = createTask(true, 1L, 2L);
//
//        ProductWorker worker = new ProductWorker(createService(), createConverter());
//        Result<List<Product>> result = worker.execute(task);
//
//        assertThat(loaderResult).isEqualTo(result);
//    }
//
//    private ProductTask createTask(boolean allTags, Long... tagIds) {
//        ProductTask task = new ProductTask();
//        task.setAllTags(allTags);
//        task.setTags(
//                Arrays.stream(tagIds).map(id -> {
//                    Tag tag = new Tag();
//                    tag.setId(id);
//                    tag.setName("tag" + id);
//                    return tag;
//                }).collect(Collectors.toSet())
//        );
//        return task;
//    }
//
//    private DTOServiceOLdOld<Product, ProductEntity> createService() {
//        return new DTOServiceOLdImplOld<>(null, createLoader(), null, createExecutor());
//    }
//
//    private Function<ProductTask, Predicate> createConverter() {
//        TestConverter converter = Mockito.mock(TestConverter.class);
//        Mockito
//                .when(converter.apply(Mockito.anyObject()))
//                .thenReturn(QProductEntity.productEntity.tagEntities.contains(new TagEntity()));
//        return converter;
//    }
//
//    private PredicateExecutorOld<Product, Predicate> createExecutor() {
//        TestExecutorOld executor = Mockito.mock(TestExecutorOld.class);
//        Mockito
//                .when(executor.execute(Mockito.anyObject()))
//                .thenReturn(executorResult);
//        return executor;
//    }
//
//    private LoaderOld<Product, ProductEntity, Long> createLoader() {
//        TestLoaderOld loader = Mockito.mock(TestLoaderOld.class);
//        Mockito
//                .when(loader.all())
//                .thenReturn(loaderResult);
//        return loader;
//    }
//
//    abstract private static class TestExecutorOld implements PredicateExecutorOld<Product, Predicate> {}
//    abstract private static class TestLoaderOld implements LoaderOld<Product, ProductEntity, Long> {}
//    abstract private static class TestConverter implements Function<ProductTask, Predicate>{}
//}