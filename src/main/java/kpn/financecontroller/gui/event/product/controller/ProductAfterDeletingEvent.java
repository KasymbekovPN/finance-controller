package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.DeleteFormEvent;

public final class ProductAfterDeletingEvent extends DeleteFormEvent<ProductViewController, Product> {
    public ProductAfterDeletingEvent(ProductViewController source, Product value) {
        super(source, value);
    }
}
