package kpn.financecontroller.gui.event.street.form;

import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.StreetForm;

public final class StreetDeleteButtonOnClickEvent extends DeleteEvent<StreetForm, Street> {
    public StreetDeleteButtonOnClickEvent(StreetForm source, Street value) {
        super(source, value);
    }
}
