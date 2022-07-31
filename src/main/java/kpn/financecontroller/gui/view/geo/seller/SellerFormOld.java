package kpn.financecontroller.gui.view.geo.seller;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.status.AttributeProcessor;
import kpn.financecontroller.gui.status.OptionalAttributeProcessor;
import kpn.financecontroller.gui.view.EditFormOld;

import java.util.List;

final public class SellerFormOld extends EditFormOld<Seller> {

    private final TextField name = new TextField();
    private final TextField url = new TextField();
    private final TextField description = new TextField();
    private final ComboBox<Address> address = new ComboBox<>();

    private final Checkbox urlCheckBox = new Checkbox();
    private final Checkbox descriptionCheckBox = new Checkbox();
    private final Checkbox addressCheckBox = new Checkbox();

    private final AttributeProcessor<Boolean, Seller> urlOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
            seller -> {return seller != null && seller.getUrl() != null;},
            seller -> {seller.setUrl(null);}
    );
    private final AttributeProcessor<Boolean, Seller> descriptionOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
            seller -> {return seller != null && seller.getDescription() != null;},
            seller -> {seller.setDescription(null);}
    );
    private final AttributeProcessor<Boolean, Seller> addressOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
            seller -> {return seller != null && seller.getAddress() != null;},
            seller -> {seller.setAddress(null);}
    );

    public SellerFormOld(List<Address> addresses) {
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
        address.setItems(addresses);
        address.setItemLabelGenerator(Address::getInfo);

        add(
                name,
                createOptionalLine(urlCheckBox, url, this::setUrlStatus),
                createOptionalLine(descriptionCheckBox, description, this::setDescriptionStatus),
                createOptionalLine(addressCheckBox, address, this::setAddressStatus),
                createButtonsLayout()
        );
    }

    @Override
    public void setValue(Seller value) {
        urlOptionalAttributeProcessor.calculate(value);
        descriptionOptionalAttributeProcessor.calculate(value);
        addressOptionalAttributeProcessor.calculate(value);

        urlCheckBox.setValue(urlOptionalAttributeProcessor.getStatus());
        descriptionCheckBox.setValue(descriptionOptionalAttributeProcessor.getStatus());
        addressCheckBox.setValue(addressOptionalAttributeProcessor.getStatus());

        super.setValue(value);
    }

    private HorizontalLayout createOptionalLine(Checkbox checkBox,
                                                Component component,
                                                HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> listener){
        checkBox.addValueChangeListener(listener);
        return new HorizontalLayout(checkBox, component);
    }

    @Override
    protected SaveFormEvent<EditFormOld<Seller>, Seller> createSaveEvent() {
        urlOptionalAttributeProcessor.clear(value);
        descriptionOptionalAttributeProcessor.clear(value);
        addressOptionalAttributeProcessor.clear(value);

        return new SellerSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<Seller>, Seller> createDeleteEvent() {
        return new SellerDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<Seller>, Seller> createCloseEvent() {
        return new SellerCloseFormEvent(this);
    }

    private void setAddressStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        addressOptionalAttributeProcessor.setStatus(event.getValue());
    }

    private void setDescriptionStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        descriptionOptionalAttributeProcessor.setStatus(event.getValue());
    }

    private void setUrlStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        urlOptionalAttributeProcessor.setStatus(event.getValue());
    }

    public static class SellerSaveFormEvent extends SaveFormEvent<EditFormOld<Seller>, Seller> {
        public SellerSaveFormEvent(EditFormOld<Seller> source, Seller value) {
            super(source, value);
        }
    }

    public static class SellerDeleteFormEvent extends DeleteFormEvent<EditFormOld<Seller>, Seller> {
        public SellerDeleteFormEvent(EditFormOld<Seller> source, Seller value) {
            super(source, value);
        }
    }

    public static class SellerCloseFormEvent extends CloseFormEvent<EditFormOld<Seller>, Seller> {
        public SellerCloseFormEvent(EditFormOld<Seller> source) {
            super(source);
        }
    }
}