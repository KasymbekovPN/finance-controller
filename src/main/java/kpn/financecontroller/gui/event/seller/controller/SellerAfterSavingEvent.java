package kpn.financecontroller.gui.event.seller.controller;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.controller.SellerViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class SellerAfterSavingEvent extends SaveFormEvent<SellerViewController, Seller> {
    public SellerAfterSavingEvent(SellerViewController source, Seller value) {
        super(source, value);
    }
}
