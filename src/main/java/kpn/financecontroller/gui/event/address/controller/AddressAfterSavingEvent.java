package kpn.financecontroller.gui.event.address.controller;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class AddressAfterSavingEvent extends SaveEvent<AddressViewController, Address> {
    public AddressAfterSavingEvent(AddressViewController source, Address value) {
        super(source, value);
    }
}
