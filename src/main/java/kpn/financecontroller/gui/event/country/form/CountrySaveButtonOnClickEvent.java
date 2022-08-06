package kpn.financecontroller.gui.event.country.form;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.CountryForm;

public final class CountrySaveButtonOnClickEvent extends SaveFormEvent<CountryForm, Country> {
    public CountrySaveButtonOnClickEvent(CountryForm source, Country value) {
        super(source, value);
    }
}
