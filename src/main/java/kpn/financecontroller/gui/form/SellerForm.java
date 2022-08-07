package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.event.seller.form.SellerCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.seller.form.SellerDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.seller.form.SellerSaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class SellerForm extends Form<Seller> {

    private final TextField name = new TextField();
    private final TextField url = new TextField();
    private final TextField description = new TextField();
    private final ComboBox<Address> address = new ComboBox<>();

    private final Checkbox urlCheckBox = new Checkbox();
    private final Checkbox descriptionCheckBox = new Checkbox();
    private final Checkbox addressCheckBox = new Checkbox();

    public SellerForm(Service<Long, Address, Predicate, Result<List<Address>>> addressService) {
        super(new Binder<>(Seller.class));
        addClassName("seller-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        url.setLabel(getTranslation("gui.label.url"));
        url.setPlaceholder(getTranslation("gui.placeholder.type-url"));

        description.setLabel(getTranslation("gui.label.description"));
        description.setPlaceholder(getTranslation("gui.placeholder.type-description"));

        address.setLabel(getTranslation("gui.label.address"));
        address.setItems(addressService.loader().all().getValue());
        address.setItemLabelGenerator(Address::getInfo);

        add(
                name,
                new HorizontalLayout(urlCheckBox, url),
                new HorizontalLayout(descriptionCheckBox, description),
                new HorizontalLayout(addressCheckBox, address),
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    public void setValue(Seller value) {
        urlCheckBox.setValue(value != null && value.getUrl() != null);
        descriptionCheckBox.setValue(value != null && value.getDescription() != null);
        addressCheckBox.setValue(value != null && value.getAddress() != null);
        super.setValue(value);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        if (!urlCheckBox.getValue()){
            value.setUrl(null);
        }
        if (!descriptionCheckBox.getValue()){
            value.setDescription(null);
        }
        if (!addressCheckBox.getValue()){
            value.setAddress(null);
        }
        return new SellerSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new SellerCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new SellerDeleteButtonOnClickEvent(this, value);
    }
}
