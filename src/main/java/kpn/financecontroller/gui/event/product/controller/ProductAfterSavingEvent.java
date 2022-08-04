package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class ProductAfterSavingEvent extends SaveFormEvent<ProductViewController, Product> {
    public ProductAfterSavingEvent(ProductViewController source, Product value) {
        super(source, value);
    }
}
