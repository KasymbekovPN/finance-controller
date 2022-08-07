package kpn.financecontroller.gui.event.address.form;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.AddressForm;

public final class AddressCancelButtonOnClickEvent extends CancelEvent<AddressForm, Address> {
    public AddressCancelButtonOnClickEvent(AddressForm source) {
        super(source);
    }
}
