package kpn.financecontroller.gui.event.address.form;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.AddressForm;

public final class AddressSaveButtonOnClickEvent extends SaveEvent<AddressForm, Address> {
    public AddressSaveButtonOnClickEvent(AddressForm source, Address value) {
        super(source, value);
    }
}
