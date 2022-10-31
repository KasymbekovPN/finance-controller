package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.Component;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.data.services.FutureInterfaceService;
import kpn.lib.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public final class ActionExecutionService implements FutureInterfaceService<Action, Result<Component>> {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Function<Action, Callable<Result<Component>>> factory;

    @Override
    public Future<Result<Component>> calculate(Action action) {
        return executor.submit(factory.apply(action));
    }
}
