package kpn.financecontroller.gui.event.seller.form;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.SellerForm;

public final class SellerDeleteButtonOnClickEvent extends DeleteEvent<SellerForm, Seller> {
    public SellerDeleteButtonOnClickEvent(SellerForm source, Seller value) {
        super(source, value);
    }
}
