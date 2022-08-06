package kpn.financecontroller.gui.event.country.form;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.CountryForm;

public final class CountrySaveButtonOnClickEvent extends SaveEvent<CountryForm, Country> {
    public CountrySaveButtonOnClickEvent(CountryForm source, Country value) {
        super(source, value);
    }
}
