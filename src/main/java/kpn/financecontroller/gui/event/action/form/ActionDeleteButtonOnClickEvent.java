package kpn.financecontroller.gui.event.action.form;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.ActionForm;

public final class ActionDeleteButtonOnClickEvent extends DeleteEvent<ActionForm, Action> {
    public ActionDeleteButtonOnClickEvent(ActionForm source, Action value) {
        super(source, value);
    }
}
