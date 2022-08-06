package kpn.financecontroller.gui.event.country.controller;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class CountryAfterDeletingEvent extends DeleteEvent<CountryViewController, Country> {
    public CountryAfterDeletingEvent(CountryViewController source, Country value) {
        super(source, value);
    }
}
