package kpn.financecontroller.data.services;

import com.vaadin.flow.component.*;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.action.display.ActionStartForProcessorEvent;
import kpn.financecontroller.gui.event.action.service.NewDisplayComponentEvent;
import kpn.financecontroller.gui.external.buidler.MultilineComponentBuilder;
import org.springframework.stereotype.Service;

@Service
@Tag(Tag.OBJECT)
public final class ActionProcessingService extends Component {

    // TODO: 04.09.2022 uncomment
//    @Autowired
//    private final ActionWorker worker;

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void handleActionStart(ActionStartForProcessorEvent event) {
        Action action = event.getAction();
        HasSize component = checkAction(action) ? createComponent() : createDefaultComponent();
        fireEvent(new NewDisplayComponentEvent(this, component));
    }

    private boolean checkAction(Action action) {
        return action != null && action.getAlgorithm() != null;
    }

    // TODO: 04.09.2022 must use worker
    private HasSize createComponent() {
        return new MultilineComponentBuilder()
                .append("SOME RESULT")
                .build();
    }

    private HasSize createDefaultComponent() {
        return new MultilineComponentBuilder()
                .append(getTranslation("gui.text.algorithm.null"))
                .build();
    }
}
