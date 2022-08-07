package kpn.financecontroller.gui.event.seller.form;

import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.SellerForm;

public final class SellerSaveButtonOnClickEvent extends SaveEvent<SellerForm, Seller> {
    public SellerSaveButtonOnClickEvent(SellerForm source, Seller value) {
        super(source, value);
    }
}
