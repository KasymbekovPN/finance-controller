package kpn.financecontroller.gui.event.street.form;

import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.StreetForm;

public final class StreetSaveButtonOnClickEvent extends SaveEvent<StreetForm, Street> {
    public StreetSaveButtonOnClickEvent(StreetForm source, Street value) {
        super(source, value);
    }
}
