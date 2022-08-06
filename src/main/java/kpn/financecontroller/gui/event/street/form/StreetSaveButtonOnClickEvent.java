package kpn.financecontroller.gui.event.street.form;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.StreetForm;

public final class StreetSaveButtonOnClickEvent extends SaveFormEvent<StreetForm, Street> {
    public StreetSaveButtonOnClickEvent(StreetForm source, Street value) {
        super(source, value);
    }
}
