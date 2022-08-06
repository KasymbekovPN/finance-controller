package kpn.financecontroller.gui.event.country.form;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.CountryForm;

public final class CountryDeleteButtonOnClickEvent extends DeleteFormEvent<CountryForm, Country> {
    public CountryDeleteButtonOnClickEvent(CountryForm source, Country value) {
        super(source, value);
    }
}
