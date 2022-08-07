package kpn.financecontroller.gui.event.region.controller;

import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class RegionAfterSavingEvent extends SaveEvent<RegionViewController, Region> {
    public RegionAfterSavingEvent(RegionViewController source, Region value) {
        super(source, value);
    }
}
