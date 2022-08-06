package kpn.financecontroller.gui.event.city.controller;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.controller.CityViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class CityAfterSavingEvent extends SaveEvent<CityViewController, City> {
    public CityAfterSavingEvent(CityViewController source, City value) {
        super(source, value);
    }
}
