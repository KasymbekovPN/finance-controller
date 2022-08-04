package kpn.financecontroller.gui.event.product.form;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.ProductForm;

public final class ProductSaveButtonOnClickEvent extends SaveFormEvent<ProductForm, Product> {
    public ProductSaveButtonOnClickEvent(ProductForm source, Product value) {
        super(source, value);
    }
}
