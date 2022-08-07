package kpn.financecontroller.gui.event.city.controller;

import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.gui.controller.CityViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class CityAfterDeletingEvent extends DeleteEvent<CityViewController, City> {
    public CityAfterDeletingEvent(CityViewController source, City value) {
        super(source, value);
    }
}
