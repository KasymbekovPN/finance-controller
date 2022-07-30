package kpn.financecontroller.gui.views.geo.address;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditFormOld;

import java.util.List;

final public class AddressFormOld extends EditFormOld<Address> {

    private final TextField name = new TextField();
    private final ComboBox<Street> street = new ComboBox<>();

    public AddressFormOld(List<Street> streets) {
        super(new Binder<>(Address.class));

        addClassName("address-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        street.setLabel(getTranslation("gui.label.street"));
        street.setItems(streets);
        street.setItemLabelGenerator(Street::getInfo);

        add(
                name,
                street,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditFormOld<Address>, Address> createSaveEvent() {
        return new AddressSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<Address>, Address> createDeleteEvent() {
        return new AddressDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<Address>, Address> createCloseEvent() {
        return new AddressCloseFormEvent(this);
    }

    public static class AddressSaveFormEvent extends SaveFormEvent<EditFormOld<Address>, Address> {
        public AddressSaveFormEvent(EditFormOld<Address> source, Address value) {
            super(source, value);
        }
    }

    public static class AddressDeleteFormEvent extends DeleteFormEvent<EditFormOld<Address>, Address> {
        public AddressDeleteFormEvent(EditFormOld<Address> source, Address value) {
            super(source, value);
        }
    }

    public static class AddressCloseFormEvent extends CloseFormEvent<EditFormOld<Address>, Address> {
        public AddressCloseFormEvent(EditFormOld<Address> source) {
            super(source);
        }
    }
}
