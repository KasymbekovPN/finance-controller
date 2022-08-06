package kpn.financecontroller.gui.event.city.controller;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.controller.CityViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class CityAfterSavingEvent extends SaveFormEvent<CityViewController, City> {
    public CityAfterSavingEvent(CityViewController source, City value) {
        super(source, value);
    }
}
