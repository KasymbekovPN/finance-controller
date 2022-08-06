package kpn.financecontroller.gui.event.address.controller;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.DeleteFormEvent;

public final class AddressAfterDeletingEvent extends DeleteFormEvent<AddressViewController, Address> {
    public AddressAfterDeletingEvent(AddressViewController source, Address value) {
        super(source, value);
    }
}
