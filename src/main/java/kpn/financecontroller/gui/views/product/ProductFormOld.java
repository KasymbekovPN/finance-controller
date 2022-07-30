package kpn.financecontroller.gui.views.product;

import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditFormOld;

import java.util.List;

final public class ProductFormOld extends EditFormOld<Product> {

    private final TextField name = new TextField();
    private final MultiSelectListBox<Tag> tags = new MultiSelectListBox<>();

    public ProductFormOld(List<Tag> tagList) {
        super(new Binder<>(Product.class));
        addClassName("product-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        tags.setItems(tagList);

        add(
                name,
                tags,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditFormOld<Product>, Product> createSaveEvent() {
        return new ProductSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<Product>, Product> createDeleteEvent() {
        return new ProductDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<Product>, Product> createCloseEvent() {
        return new ProductCloseFormEvent(this);
    }

    public static class ProductSaveFormEvent extends SaveFormEvent<EditFormOld<Product>, Product> {
        public ProductSaveFormEvent(EditFormOld<Product> source, Product value) {
            super(source, value);
        }
    }

    public static class ProductDeleteFormEvent extends DeleteFormEvent<EditFormOld<Product>, Product> {
        public ProductDeleteFormEvent(EditFormOld<Product> source, Product value) {
            super(source, value);
        }
    }

    public static class ProductCloseFormEvent extends CloseFormEvent<EditFormOld<Product>, Product> {
        public ProductCloseFormEvent(EditFormOld<Product> source) {
            super(source);
        }
    }
}
