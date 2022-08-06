package kpn.financecontroller.gui.event.region.form;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.RegionForm;

public final class RegionCancelButtonOnClickEvent extends CancelEvent<RegionForm, Region> {
    public RegionCancelButtonOnClickEvent(RegionForm source) {
        super(source);
    }
}
