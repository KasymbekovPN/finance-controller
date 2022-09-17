package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.data.services.dto.service.TagDtoDecorator;
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

class TagServiceWrapperTest {

    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Tag>> expectedResult
                = ImmutableResult.<List<Tag>>fail("wrapper."+ TagServiceWrapper.class.getSimpleName() + ".service.null");

        TagServiceWrapper.unregisterService(TagDtoDecorator.class);
        Result<List<Tag>> result = new TagServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Tag>> expectedResult = ImmutableResult.<List<Tag>>ok(List.of(createDomain()));

        TagServiceWrapper.registerService(new TagDtoDecorator(createService()));
        Result<List<Tag>> result = new TagServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll_ServiceNull() {
        ImmutableResult<List<Tag>> expectedResult
                = ImmutableResult.<List<Tag>>fail("wrapper."+ TagServiceWrapper.class.getSimpleName() + ".service.null");

        TagServiceWrapper.unregisterService(TagDtoDecorator.class);
        Result<List<Tag>> result = new TagServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll() throws DTOException {
        ImmutableResult<List<Tag>> expectedResult = ImmutableResult.<List<Tag>>ok(List.of(createDomain()));

        TagServiceWrapper.registerService(new TagDtoDecorator(createService()));
        Result<List<Tag>> result = new TagServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Tag, Predicate, Result<List<Tag>>> createService() throws DTOException {
        TagPredicateExecutor executor = Mockito.mock(TagPredicateExecutor.class);
        DefaultExecutorResult<Tag> result = new DefaultExecutorResult<>(createDomain());
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(result);

        TagLoadingExecutor loader = Mockito.mock(TagLoadingExecutor.class);
        Mockito
                .when(loader.load())
                .thenReturn(result);

        return new ServiceBuider<Long, Tag, Predicate, Result<List<Tag>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and()
                .loadingAspectBuilder()
                .executorAll(loader)
                .and()
                .build();
    }

    private Tag createDomain() {
        Tag tag = new Tag();
        tag.setId(123L);
        tag.setName("tag name");
        return tag;
    }

    private abstract static class TagPredicateExecutor implements PredicateExecutor<Predicate, Tag> {}
    private abstract static class TagLoadingExecutor implements CompletelyLoadingExecutor<Tag> {}
}