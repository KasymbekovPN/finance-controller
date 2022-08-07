package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class ProductAfterSavingEvent extends SaveEvent<ProductViewController, Product> {
    public ProductAfterSavingEvent(ProductViewController source, Product value) {
        super(source, value);
    }
}
