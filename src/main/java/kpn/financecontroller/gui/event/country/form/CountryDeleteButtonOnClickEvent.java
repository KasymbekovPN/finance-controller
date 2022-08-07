package kpn.financecontroller.gui.event.country.form;

import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.CountryForm;

public final class CountryDeleteButtonOnClickEvent extends DeleteEvent<CountryForm, Country> {
    public CountryDeleteButtonOnClickEvent(CountryForm source, Country value) {
        super(source, value);
    }
}
