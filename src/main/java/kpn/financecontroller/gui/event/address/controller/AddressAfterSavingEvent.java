package kpn.financecontroller.gui.event.address.controller;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class AddressAfterSavingEvent extends SaveFormEvent<AddressViewController, Address> {
    public AddressAfterSavingEvent(AddressViewController source, Address value) {
        super(source, value);
    }
}
