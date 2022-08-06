package kpn.financecontroller.gui.event.address.form;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.AddressForm;

public final class AddressDeleteButtonOnClickEvent extends DeleteFormEvent<AddressForm, Address> {
    public AddressDeleteButtonOnClickEvent(AddressForm source, Address value) {
        super(source, value);
    }
}
