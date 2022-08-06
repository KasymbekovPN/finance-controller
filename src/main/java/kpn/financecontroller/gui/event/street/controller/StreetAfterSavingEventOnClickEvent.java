package kpn.financecontroller.gui.event.street.controller;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.controller.StreetViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class StreetAfterSavingEventOnClickEvent extends SaveEvent<StreetViewController, Street> {
    public StreetAfterSavingEventOnClickEvent(StreetViewController source, Street value) {
        super(source, value);
    }
}
