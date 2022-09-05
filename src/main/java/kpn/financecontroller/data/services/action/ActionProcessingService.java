package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.*;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.action.display.ActionStartForProcessorEvent;
import kpn.financecontroller.gui.event.action.service.NewDisplayComponentEvent;
import kpn.financecontroller.gui.external.builder.MultilineComponentBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Tag(Tag.OBJECT)
@RequiredArgsConstructor
public final class ActionProcessingService extends Component {
    @Autowired
    private final ActionWorker worker;

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
        Object execResult = worker.execute(algorithm);
        List<Class<?>> interfaces = List.of(execResult.getClass().getInterfaces());
        return interfaces.contains(HasSize.class)
                ? (HasSize) execResult
                : createDefaultComponent("gui.text.algorithm.invalid");
    }

    private HasSize createDefaultComponent(String code) {
        return new MultilineComponentBuilder()
                .append(getTranslation(code))
                .build();
    }
}
