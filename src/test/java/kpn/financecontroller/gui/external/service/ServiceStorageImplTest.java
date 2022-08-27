package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.junit.jupiter.api.Test;
import support.TestDomain;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceStorageImplTest {
    @Test
    void shouldCheckRegistration_firstTime() {
        Object registered = new ServiceStorageImpl().register(new TestService());
        assertThat(registered).isNull();
    }

    @Test
    void shouldCheckRegistration_notFirstTime() {
        TestService service = new TestService();
        ServiceStorageImpl storage = new ServiceStorageImpl();
        storage.register(service);
        Object registered = storage.register(service);

        assertThat(registered).isEqualTo(service);
    }

    @Test
    void shouldCheckGetting_ifAbsent() {
        ServiceStorageImpl storage = new ServiceStorageImpl();

        TestService service = storage.get(TestService.class);
        assertThat(service).isNull();
    }

    @Test
    void shouldCheckGetting() {
        TestService service = new TestService();

        ServiceStorageImpl storage = new ServiceStorageImpl();
        storage.register(service);
        TestService gottenService = storage.get(TestService.class);
        assertThat(gottenService).isEqualTo(service);
    }

    private static class TestService implements Service<Long, TestDomain, Predicate, Result<List<TestDomain>>>{
        @Override
        public SavingAspect<TestDomain, Result<List<TestDomain>>> saver() {return null;}

        @Override
        public LoadingAspect<Long, Result<List<TestDomain>>> loader() {return null;}

        @Override
        public DeletingAspect<Long, Result<List<TestDomain>>> deleter() {return null;}

        @Override
        public PredicateAspect<Predicate, Result<List<TestDomain>>> executor() {return null;}
    }
}