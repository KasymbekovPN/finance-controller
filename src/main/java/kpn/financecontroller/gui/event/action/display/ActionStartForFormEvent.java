package kpn.financecontroller.gui.event.action.display;

import com.vaadin.flow.component.ComponentEvent;
import kpn.financecontroller.gui.display.ActionDisplay;

public final class ActionStartForFormEvent extends ComponentEvent<ActionDisplay> {
    public ActionStartForFormEvent(ActionDisplay source) {
        super(source, false);
    }
}
