package kpn.financecontroller.gui.event.product.form;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.ProductForm;

public final class ProductCancelButtonOnClickEvent extends CancelEvent<ProductForm, Product> {
    public ProductCancelButtonOnClickEvent(ProductForm source) {
        super(source);
    }
}
