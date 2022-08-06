package kpn.financecontroller.gui.event.street.form;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.StreetForm;

public final class StreetDeleteButtonOnClickEvent extends DeleteFormEvent<StreetForm, Street> {
    public StreetDeleteButtonOnClickEvent(StreetForm source, Street value) {
        super(source, value);
    }
}
