package kpn.financecontroller.gui.event.seller.form;

import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.SellerForm;

public final class SellerCancelButtonOnClickEvent extends CancelEvent<SellerForm, Seller> {
    public SellerCancelButtonOnClickEvent(SellerForm source) {
        super(source);
    }
}
