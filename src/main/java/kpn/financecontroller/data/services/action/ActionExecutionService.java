package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.Component;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.data.services.FutureInterfaceService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public final class ActionExecutionService implements FutureInterfaceService<Action, Component> {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public Future<Component> calculate(Action input) {
        return null;
    }

    /*

    @Service
public final class ActionServiceImpl implements ActionService {
    private final ExecutorService executor = Executors.newCachedThreadPool();

//    private Supplier<>

    @Override
    public Future<Component> calculate(Action action) {



        return null;
    }

//    private static class Task implements Callable<Component>{
//        @Override
//        public Component call() throws Exception {
//            return null;
//        }
//    }
}

     */
}
