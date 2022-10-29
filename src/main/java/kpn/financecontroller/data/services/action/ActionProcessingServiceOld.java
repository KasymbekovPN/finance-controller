package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.*;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.action.display.ActionStartForProcessorEvent;
import kpn.financecontroller.gui.event.action.service.NewDisplayComponentEvent;
import kpn.financecontroller.gui.external.builder.MultilineComponentBuilder;
import kpn.lib.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: 29.10.2022 del
@Service
@Tag(Tag.OBJECT)
@RequiredArgsConstructor
public final class ActionProcessingServiceOld extends Component {
    @Autowired
    private final ActionWorkerOld worker;

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void handleActionStart(ActionStartForProcessorEvent event) {
        Action action = event.getAction();
        HasSize component = checkAction(action)
                ? createComponent(action.getAlgorithm())
                : createDefaultComponent("gui.text.algorithm.null");
        fireEvent(new NewDisplayComponentEvent(this, component));
    }

    private boolean checkAction(Action action) {
        return action != null && action.getAlgorithm() != null;
    }

    private HasSize createComponent(String algorithm) {
        Result<Object> executionResult = worker.execute(algorithm);
        if (executionResult.isSuccess()){
            Object value = executionResult.getValue();
            if (value != null){
                List<Class<?>> interfaces = List.of(value.getClass().getInterfaces());
                return interfaces.contains(HasSize.class)
                        ? (HasSize) value
                        : createDefaultComponent("gui.text.algorithm.returned.invalidType", value.getClass().getSimpleName());
            }
            return createDefaultComponent("gui.text.algorithm.returned.null");
        }
        return createDefaultComponent(executionResult.getSeed().getCode(), executionResult.getSeed().getArgs());
    }

    private HasSize createDefaultComponent(String code, Object... args) {
        return new MultilineComponentBuilder()
                .append(getTranslation(code, args))
                .build();
    }
}
