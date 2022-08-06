package kpn.financecontroller.gui.event.country.controller;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class CountryAfterSavingEvent extends SaveFormEvent<CountryViewController, Country> {
    public CountryAfterSavingEvent(CountryViewController source, Country value) {
        super(source, value);
    }
}
