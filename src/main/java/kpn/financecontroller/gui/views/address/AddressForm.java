package kpn.financecontroller.gui.views.address;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class AddressForm extends EditForm<Address> {

    private final TextField name = new TextField();
    private final ComboBox<Street> street = new ComboBox<>();

    public AddressForm(List<Street> streets) {
        super(new Binder<>(Address.class));

        addClassName("address-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        street.setLabel(getTranslation("gui.street"));
        street.setItems(streets);
        street.setItemLabelGenerator(Street::getFullName);

        add(
                name,
                street,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Address>, Address> createSaveEvent() {
        return new AddressSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Address>, Address> createDeleteEvent() {
        return new AddressDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Address>, Address> createCloseEvent() {
        return new AddressCloseFormEvent(this);
    }

    public static class AddressSaveFormEvent extends SaveFormEvent<EditForm<Address>, Address> {
        public AddressSaveFormEvent(EditForm<Address> source, Address value) {
            super(source, value);
        }
    }

    public static class AddressDeleteFormEvent extends DeleteFormEvent<EditForm<Address>, Address> {
        public AddressDeleteFormEvent(EditForm<Address> source, Address value) {
            super(source, value);
        }
    }

    public static class AddressCloseFormEvent extends CloseFormEvent<EditForm<Address>, Address> {
        public AddressCloseFormEvent(EditForm<Address> source) {
            super(source);
        }
    }
}
