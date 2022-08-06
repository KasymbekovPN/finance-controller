package kpn.financecontroller.gui.event.country.form;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.form.CountryForm;

public final class CountryCancelButtonOnClickEvent extends CloseFormEvent<CountryForm, Country> {
    public CountryCancelButtonOnClickEvent(CountryForm source) {
        super(source);
    }
}
