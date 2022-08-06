package kpn.financecontroller.gui.event.city.controller;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.controller.CityViewController;
import kpn.financecontroller.gui.event.DeleteFormEvent;

public final class CityAfterDeletingEvent extends DeleteFormEvent<CityViewController, City> {
    public CityAfterDeletingEvent(CityViewController source, City value) {
        super(source, value);
    }
}
