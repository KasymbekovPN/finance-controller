package kpn.financecontroller.gui.event.action.display;

import com.vaadin.flow.component.ComponentEvent;
import kpn.financecontroller.gui.display.ActionDisplay;

public final class ActionRunButtonOnClickEvent extends ComponentEvent<ActionDisplay> {
    public ActionRunButtonOnClickEvent(ActionDisplay source) {
        super(source, false);
    }
}
