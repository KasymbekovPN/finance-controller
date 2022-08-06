package kpn.financecontroller.gui.event.street.controller;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.controller.StreetViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class StreetAfterDeletingEventOnClickEvent extends DeleteEvent<StreetViewController, Street> {
    public StreetAfterDeletingEventOnClickEvent(StreetViewController source, Street value) {
        super(source, value);
    }
}
