package kpn.financecontroller.gui.event.action.form;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.ActionForm;

public final class ActionCancelButtonOnClickEvent extends CancelEvent<ActionForm, Action> {
    public ActionCancelButtonOnClickEvent(ActionForm source) {
        super(source);
    }
}
