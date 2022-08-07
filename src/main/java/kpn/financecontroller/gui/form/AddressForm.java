package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.gui.event.address.form.AddressCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.address.form.AddressDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.address.form.AddressSaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class AddressForm extends Form<Address> {
    private final TextField name = new TextField();
    private final ComboBox<Street> street = new ComboBox<>();

    public AddressForm(Service<Long, Street, Predicate, Result<List<Street>>> streetService) {
        super(new Binder<>(Address.class));

        addClassName("address-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        street.setLabel(getTranslation("gui.label.street"));
        street.setItems(streetService.loader().all().getValue());
        street.setItemLabelGenerator(Street::getInfo);

        add(
                name,
                street,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new AddressSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new AddressCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new AddressDeleteButtonOnClickEvent(this, value);
    }
}
