package kpn.financecontroller.gui.event.action.controller;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class ActionAfterSavingEvent extends SaveEvent<ActionViewController, Action> {
    public ActionAfterSavingEvent(ActionViewController source, Action value) {
        super(source, value);
    }
}
