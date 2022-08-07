package kpn.financecontroller.gui.event.seller.controller;

import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.controller.SellerViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class SellerAfterSavingEvent extends SaveEvent<SellerViewController, Seller> {
    public SellerAfterSavingEvent(SellerViewController source, Seller value) {
        super(source, value);
    }
}
