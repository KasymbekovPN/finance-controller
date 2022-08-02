package kpn.financecontroller.gui.event.seller.form;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.SellerForm;

public final class SellerDeleteButtonOnClickEvent extends DeleteFormEvent<SellerForm, Seller> {
    public SellerDeleteButtonOnClickEvent(SellerForm source, Seller value) {
        super(source, value);
    }
}
