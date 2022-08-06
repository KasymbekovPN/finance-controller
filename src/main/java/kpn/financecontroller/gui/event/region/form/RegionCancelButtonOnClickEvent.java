package kpn.financecontroller.gui.event.region.form;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.form.RegionForm;

public final class RegionCancelButtonOnClickEvent extends CloseFormEvent<RegionForm, Region> {
    public RegionCancelButtonOnClickEvent(RegionForm source) {
        super(source);
    }
}
