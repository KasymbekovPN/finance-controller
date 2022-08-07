package kpn.financecontroller.gui.event.seller.controller;

import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.controller.SellerViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class SellerAfterDeletingEvent extends DeleteEvent<SellerViewController, Seller> {
    public SellerAfterDeletingEvent(SellerViewController source, Seller value) {
        super(source, value);
    }
}
