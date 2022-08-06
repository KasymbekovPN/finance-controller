package kpn.financecontroller.gui.event.country.controller;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.DeleteFormEvent;

public final class CountryAfterDeletingEvent extends DeleteFormEvent<CountryViewController, Country> {
    public CountryAfterDeletingEvent(CountryViewController source, Country value) {
        super(source, value);
    }
}
