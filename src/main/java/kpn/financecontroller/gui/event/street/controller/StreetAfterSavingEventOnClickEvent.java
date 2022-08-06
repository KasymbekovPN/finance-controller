package kpn.financecontroller.gui.event.street.controller;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.controller.StreetViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class StreetAfterSavingEventOnClickEvent extends SaveFormEvent<StreetViewController, Street> {
    public StreetAfterSavingEventOnClickEvent(StreetViewController source, Street value) {
        super(source, value);
    }
}
