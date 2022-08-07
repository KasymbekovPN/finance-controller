package kpn.financecontroller.gui.event.address.form;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.AddressForm;

public final class AddressDeleteButtonOnClickEvent extends DeleteEvent<AddressForm, Address> {
    public AddressDeleteButtonOnClickEvent(AddressForm source, Address value) {
        super(source, value);
    }
}
