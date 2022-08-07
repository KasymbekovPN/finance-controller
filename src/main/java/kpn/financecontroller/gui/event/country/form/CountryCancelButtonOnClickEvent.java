package kpn.financecontroller.gui.event.country.form;

import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.CountryForm;

public final class CountryCancelButtonOnClickEvent extends CancelEvent<CountryForm, Country> {
    public CountryCancelButtonOnClickEvent(CountryForm source) {
        super(source);
    }
}
