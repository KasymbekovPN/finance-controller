package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.Component;
import groovy.lang.GroovyShell;
import kpn.financecontroller.data.domain.Action;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public final class ActionTask implements Callable<Result<Component>> {
    private final GroovyShell shell = new GroovyShell();
    private final String header;
    private final Action action;

    @Override
    public Result<Component> call() throws Exception {
        if (header == null){
            return ImmutableResult.<Component>fail("action-task-execution.header.null");
        }
        if (action == null){
            return ImmutableResult.<Component>fail("action-task-execution.action.null");
        }

        String algorithm = action.getAlgorithm();
        if (algorithm == null){
            return ImmutableResult.<Component>fail("action-task-execution.algorithm.null");
        }

        try{
            Object object = shell.evaluate(header + "\n" + algorithm);
            if (object == null){
                return ImmutableResult.<Component>fail("action-task-execution.algorithm.returns-null");
            }
            return isChildOfComponent(object.getClass())
                    ? ImmutableResult.<Component>ok((Component) object)
                    : ImmutableResult.<Component>fail("action-task-execution.algorithm.returns-not-component");
        } catch (Throwable t){
            return ImmutableResult.<Component>bFail("action-task-execution.algorithm.exception")
                    .arg(t.getClass().getSimpleName())
                    .arg(t.getMessage())
                    .build();
        }
    }

    private boolean isChildOfComponent(Class<?> type) {
        if (type.equals(Component.class)){
            return true;
        }
        if (type.equals(Object.class)){
            return false;
        }
        return isChildOfComponent(type.getSuperclass());
    }
}
