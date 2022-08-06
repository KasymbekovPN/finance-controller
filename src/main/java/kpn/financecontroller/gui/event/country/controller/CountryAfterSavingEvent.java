package kpn.financecontroller.gui.event.country.controller;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class CountryAfterSavingEvent extends SaveEvent<CountryViewController, Country> {
    public CountryAfterSavingEvent(CountryViewController source, Country value) {
        super(source, value);
    }
}
