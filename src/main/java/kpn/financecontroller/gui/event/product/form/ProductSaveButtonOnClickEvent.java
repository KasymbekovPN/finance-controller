package kpn.financecontroller.gui.event.product.form;

import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.ProductForm;

public final class ProductSaveButtonOnClickEvent extends SaveEvent<ProductForm, Product> {
    public ProductSaveButtonOnClickEvent(ProductForm source, Product value) {
        super(source, value);
    }
}
