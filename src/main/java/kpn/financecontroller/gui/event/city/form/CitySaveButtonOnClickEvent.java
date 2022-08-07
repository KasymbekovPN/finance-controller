package kpn.financecontroller.gui.event.city.form;

import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.CityForm;

public final class CitySaveButtonOnClickEvent extends SaveEvent<CityForm, City> {
    public CitySaveButtonOnClickEvent(CityForm source, City value) {
        super(source, value);
    }
}
