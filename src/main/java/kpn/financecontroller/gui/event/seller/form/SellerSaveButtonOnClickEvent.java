package kpn.financecontroller.gui.event.seller.form;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.SellerForm;

public final class SellerSaveButtonOnClickEvent extends SaveFormEvent<SellerForm, Seller> {
    public SellerSaveButtonOnClickEvent(SellerForm source, Seller value) {
        super(source, value);
    }
}
