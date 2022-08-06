package kpn.financecontroller.gui.event.region.controller;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class RegionAfterSavingEvent extends SaveFormEvent<RegionViewController, Region> {
    public RegionAfterSavingEvent(RegionViewController source, Region value) {
        super(source, value);
    }
}
