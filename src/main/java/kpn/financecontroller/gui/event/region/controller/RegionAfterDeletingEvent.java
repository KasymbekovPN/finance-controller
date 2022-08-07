package kpn.financecontroller.gui.event.region.controller;

import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class RegionAfterDeletingEvent extends DeleteEvent<RegionViewController, Region> {
    public RegionAfterDeletingEvent(RegionViewController source, Region value) {
        super(source, value);
    }
}
