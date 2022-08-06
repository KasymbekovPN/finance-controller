package kpn.financecontroller.gui.event.address.form;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.AddressForm;

public final class AddressSaveButtonOnClickEvent extends SaveFormEvent<AddressForm, Address> {
    public AddressSaveButtonOnClickEvent(AddressForm source, Address value) {
        super(source, value);
    }
}
