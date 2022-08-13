package kpn.financecontroller.gui.event.action.controller;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class ActionAfterDeletingEvent extends DeleteEvent<ActionViewController, Action> {
    public ActionAfterDeletingEvent(ActionViewController source, Action value) {
        super(source, value);
    }
}
