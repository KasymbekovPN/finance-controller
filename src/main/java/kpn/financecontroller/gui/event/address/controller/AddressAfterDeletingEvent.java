package kpn.financecontroller.gui.event.address.controller;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class AddressAfterDeletingEvent extends DeleteEvent<AddressViewController, Address> {
    public AddressAfterDeletingEvent(AddressViewController source, Address value) {
        super(source, value);
    }
}
