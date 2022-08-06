package kpn.financecontroller.gui.event.city.form;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.CityForm;

public final class CityDeleteButtonOnClickEvent extends DeleteFormEvent<CityForm, City> {
    public CityDeleteButtonOnClickEvent(CityForm source, City value) {
        super(source, value);
    }
}
