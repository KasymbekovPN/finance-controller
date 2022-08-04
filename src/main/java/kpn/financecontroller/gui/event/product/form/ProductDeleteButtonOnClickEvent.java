package kpn.financecontroller.gui.event.product.form;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.ProductForm;

public final class ProductDeleteButtonOnClickEvent extends DeleteFormEvent<ProductForm, Product> {
    public ProductDeleteButtonOnClickEvent(ProductForm source, Product value) {
        super(source, value);
    }
}
