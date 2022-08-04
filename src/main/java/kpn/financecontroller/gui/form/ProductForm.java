package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.product.form.ProductCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.product.form.ProductDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.product.form.ProductSaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class ProductForm extends Form<Product> {
    private final TextField name = new TextField();
    private final MultiSelectListBox<Tag> tags = new MultiSelectListBox<>();

    public ProductForm(Service<Long, Tag, Predicate, Result<List<Tag>>> service) {
        super(new Binder<>(Product.class));
        addClassName("product-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        tags.setItems(service.loader().all().getValue());

        add(
                name,
                tags,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new ProductSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new ProductCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new ProductDeleteButtonOnClickEvent(this, value);
    }
}
