package kpn.financecontroller.gui.event.city.form;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.CityForm;

public final class CitySaveButtonOnClickEvent extends SaveFormEvent<CityForm, City> {
    public CitySaveButtonOnClickEvent(CityForm source, City value) {
        super(source, value);
    }
}
