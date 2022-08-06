package kpn.financecontroller.gui.event.region.form;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.RegionForm;

public final class RegionSaveButtonOnClickEvent extends SaveFormEvent<RegionForm, Region> {
    public RegionSaveButtonOnClickEvent(RegionForm source, Region value) {
        super(source, value);
    }
}
