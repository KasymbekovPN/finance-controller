package kpn.financecontroller.gui.event.region.form;

import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.RegionForm;

public final class RegionDeleteButtonOnClickEvent extends DeleteEvent<RegionForm, Region> {
    public RegionDeleteButtonOnClickEvent(RegionForm source, Region value) {
        super(source, value);
    }
}
