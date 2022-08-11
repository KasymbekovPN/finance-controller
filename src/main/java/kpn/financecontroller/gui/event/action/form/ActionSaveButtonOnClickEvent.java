package kpn.financecontroller.gui.event.action.form;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.ActionForm;

public final class ActionSaveButtonOnClickEvent extends SaveEvent<ActionForm, Action> {
    public ActionSaveButtonOnClickEvent(ActionForm source, Action value) {
        super(source, value);
    }
}
