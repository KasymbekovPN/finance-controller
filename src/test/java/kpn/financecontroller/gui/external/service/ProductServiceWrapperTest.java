package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.services.dto.service.ProductDtoDecorator;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.loading.CompletelyLoadingExecutor;
import kpn.lib.executor.predicate.PredicateExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceWrapperTest {

    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Product>> expectedResult
                = ImmutableResult.<List<Product>>fail("wrapper."+ ProductServiceWrapper.class.getSimpleName() + ".service.null");

        ProductServiceWrapper.unregisterService(ProductDtoDecorator.class);
        Result<List<Product>> result = new ProductServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>ok(List.of(createDomain()));

        TagServiceWrapper.registerService(new ProductDtoDecorator(createService()));
        Result<List<Product>> result = new ProductServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll_ServiceNull() {
        ImmutableResult<List<Product>> expectedResult
                = ImmutableResult.<List<Product>>fail("wrapper."+ ProductServiceWrapper.class.getSimpleName() + ".service.null");

        ProductServiceWrapper.unregisterService(ProductDtoDecorator.class);
        Result<List<Product>> result = new ProductServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll() throws DTOException {
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>ok(List.of(createDomain()));

        TagServiceWrapper.registerService(new ProductDtoDecorator(createService()));
        Result<List<Product>> result = new ProductServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    private Product createDomain() {
        Product product = new Product();
        product.setId(123L);
        product.setName("product name");
        return product;
    }

    private Service<Long, Product, Predicate, Result<List<Product>>> createService() throws DTOException {
        ProductPredicateExecutor executor = Mockito.mock(ProductPredicateExecutor.class);
        DefaultExecutorResult<Product> result = new DefaultExecutorResult<>(createDomain());
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(result);

        ProductLoadingExecutor loader = Mockito.mock(ProductLoadingExecutor.class);
        Mockito
                .when(loader.load())
                .thenReturn(result);

        return new ServiceBuider<Long, Product, Predicate, Result<List<Product>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and()
                .loadingAspectBuilder()
                .executorAll(loader)
                .and()
                .build();
    }
    private abstract static class ProductPredicateExecutor implements PredicateExecutor<Predicate, Product> {}
    private abstract static class ProductLoadingExecutor implements CompletelyLoadingExecutor<Product> {}
}