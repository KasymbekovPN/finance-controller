package kpn.financecontroller.gui.event.seller.controller;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.controller.SellerViewController;
import kpn.financecontroller.gui.event.DeleteFormEvent;

public final class SellerAfterDeletingEvent extends DeleteFormEvent<SellerViewController, Seller> {
    public SellerAfterDeletingEvent(SellerViewController source, Seller value) {
        super(source, value);
    }
}
