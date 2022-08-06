package kpn.financecontroller.gui.event.street.form;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.form.StreetForm;

public final class StreetCancelButtonOnClickEvent extends CloseFormEvent<StreetForm, Street> {
    public StreetCancelButtonOnClickEvent(StreetForm source) {
        super(source);
    }
}
