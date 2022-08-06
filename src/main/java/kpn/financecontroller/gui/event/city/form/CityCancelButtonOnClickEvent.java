package kpn.financecontroller.gui.event.city.form;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.CityForm;

public final class CityCancelButtonOnClickEvent extends CancelEvent<CityForm, City> {
    public CityCancelButtonOnClickEvent(CityForm source) {
        super(source);
    }
}
