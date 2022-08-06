package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class ProductAfterDeletingEvent extends DeleteEvent<ProductViewController, Product> {
    public ProductAfterDeletingEvent(ProductViewController source, Product value) {
        super(source, value);
    }
}
