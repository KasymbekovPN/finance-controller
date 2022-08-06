package kpn.financecontroller.gui.event.address.form;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.form.AddressForm;

public final class AddressCancelButtonOnClickEvent extends CloseFormEvent<AddressForm, Address> {
    public AddressCancelButtonOnClickEvent(AddressForm source) {
        super(source);
    }
}
