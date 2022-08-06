package kpn.financecontroller.gui.event.region.controller;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.DeleteFormEvent;

public final class RegionAfterDeletingEvent extends DeleteFormEvent<RegionViewController, Region> {
    public RegionAfterDeletingEvent(RegionViewController source, Region value) {
        super(source, value);
    }
}
