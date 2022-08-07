package kpn.financecontroller.gui.event.product.form;

import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.ProductForm;

public final class ProductDeleteButtonOnClickEvent extends DeleteEvent<ProductForm, Product> {
    public ProductDeleteButtonOnClickEvent(ProductForm source, Product value) {
        super(source, value);
    }
}
