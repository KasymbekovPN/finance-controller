package kpn.financecontroller.gui.event.action.display;

import com.vaadin.flow.component.ComponentEvent;
import kpn.financecontroller.gui.display.ActionDisplay;

public final class ActionClearButtonOnClickEvent extends ComponentEvent<ActionDisplay> {
    public ActionClearButtonOnClickEvent(ActionDisplay source) {
        super(source, false);
    }
}
