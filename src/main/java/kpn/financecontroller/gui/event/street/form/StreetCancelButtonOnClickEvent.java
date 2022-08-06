package kpn.financecontroller.gui.event.street.form;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.StreetForm;

public final class StreetCancelButtonOnClickEvent extends CancelEvent<StreetForm, Street> {
    public StreetCancelButtonOnClickEvent(StreetForm source) {
        super(source);
    }
}
