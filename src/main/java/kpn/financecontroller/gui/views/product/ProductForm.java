package kpn.financecontroller.gui.views.product;

import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class ProductForm extends EditForm<Product> {

    private final TextField name;
    private final MultiSelectListBox<Tag> tags = new MultiSelectListBox<>();

    public ProductForm(List<Tag> tagList) {
        super(new Binder<>(Product.class));
        addClassName("product-form");
        binder.bindInstanceFields(this);

        name = new TextField(getTranslation("gui.name"), getTranslation("gui.placeholder.type-name"));

        tags.setItems(tagList);

        add(
                name,
                tags,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Product>, Product> createSaveEvent() {
        return new ProductSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Product>, Product> createDeleteEvent() {
        return new ProductDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Product>, Product> createCloseEvent() {
        return new ProductCloseFormEvent(this);
    }

    public static class ProductSaveFormEvent extends SaveFormEvent<EditForm<Product>, Product> {
        public ProductSaveFormEvent(EditForm<Product> source, Product value) {
            super(source, value);
        }
    }

    public static class ProductDeleteFormEvent extends DeleteFormEvent<EditForm<Product>, Product> {
        public ProductDeleteFormEvent(EditForm<Product> source, Product value) {
            super(source, value);
        }
    }

    public static class ProductCloseFormEvent extends CloseFormEvent<EditForm<Product>, Product> {
        public ProductCloseFormEvent(EditForm<Product> source) {
            super(source);
        }
    }
}
