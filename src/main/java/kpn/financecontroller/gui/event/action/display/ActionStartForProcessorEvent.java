package kpn.financecontroller.gui.event.action.display;

import com.vaadin.flow.component.ComponentEvent;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.form.ActionForm;
import lombok.Getter;

public final class ActionStartForProcessorEvent extends ComponentEvent<ActionForm> {
    @Getter
    private final Action action;

    public ActionStartForProcessorEvent(ActionForm source, Action action) {
        super(source, false);
        this.action = action;
    }
}
