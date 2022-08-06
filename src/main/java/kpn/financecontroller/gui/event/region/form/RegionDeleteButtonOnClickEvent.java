package kpn.financecontroller.gui.event.region.form;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.RegionForm;

public final class RegionDeleteButtonOnClickEvent extends DeleteFormEvent<RegionForm, Region> {
    public RegionDeleteButtonOnClickEvent(RegionForm source, Region value) {
        super(source, value);
    }
}
