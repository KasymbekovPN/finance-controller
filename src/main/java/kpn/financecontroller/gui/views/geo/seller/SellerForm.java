// TODO: 17.07.2022 restore
//package kpn.financecontroller.gui.views.geo.seller;
//
//import com.vaadin.flow.component.AbstractField;
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.HasValue;
//import com.vaadin.flow.component.checkbox.Checkbox;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import kpn.financecontroller.data.domains.address.Address;
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.gui.events.CloseFormEvent;
//import kpn.financecontroller.gui.events.DeleteFormEvent;
//import kpn.financecontroller.gui.events.SaveFormEvent;
//import kpn.financecontroller.gui.status.AttributeProcessor;
//import kpn.financecontroller.gui.status.OptionalAttributeProcessor;
//import kpn.financecontroller.gui.views.EditForm;
//
//import java.util.List;
//
//final public class SellerForm extends EditForm<Seller> {
//
//    private final TextField name = new TextField();
//    private final TextField url = new TextField();
//    private final TextField description = new TextField();
//    private final ComboBox<Address> address = new ComboBox<>();
//
//    private final Checkbox urlCheckBox = new Checkbox();
//    private final Checkbox descriptionCheckBox = new Checkbox();
//    private final Checkbox addressCheckBox = new Checkbox();
//
//    private final AttributeProcessor<Boolean, Seller> urlOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
//            seller -> {return seller != null && seller.getUrl() != null;},
//            seller -> {seller.setUrl(null);}
//    );
//    private final AttributeProcessor<Boolean, Seller> descriptionOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
//            seller -> {return seller != null && seller.getDescription() != null;},
//            seller -> {seller.setDescription(null);}
//    );
//    private final AttributeProcessor<Boolean, Seller> addressOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
//            seller -> {return seller != null && seller.getAddress() != null;},
//            seller -> {seller.setAddress(null);}
//    );
//
//    public SellerForm(List<Address> addresses) {
//        super(new Binder<>(Seller.class));
//
//        addClassName("seller-form");
//        binder.bindInstanceFields(this);
//
//        name.setLabel(getTranslation("gui.label.name"));
//        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));
//
//        url.setLabel(getTranslation("gui.label.url"));
//        url.setPlaceholder(getTranslation("gui.placeholder.type-url"));
//
//        description.setLabel(getTranslation("gui.label.description"));
//        description.setPlaceholder(getTranslation("gui.placeholder.type-description"));
//
//        address.setLabel(getTranslation("gui.label.address"));
//        address.setItems(addresses);
//        address.setItemLabelGenerator(Address::getInfo);
//
//        add(
//                name,
//                createOptionalLine(urlCheckBox, url, this::setUrlStatus),
//                createOptionalLine(descriptionCheckBox, description, this::setDescriptionStatus),
//                createOptionalLine(addressCheckBox, address, this::setAddressStatus),
//                createButtonsLayout()
//        );
//    }
//
//    @Override
//    public void setValue(Seller value) {
//        urlOptionalAttributeProcessor.calculate(value);
//        descriptionOptionalAttributeProcessor.calculate(value);
//        addressOptionalAttributeProcessor.calculate(value);
//
//        urlCheckBox.setValue(urlOptionalAttributeProcessor.getStatus());
//        descriptionCheckBox.setValue(descriptionOptionalAttributeProcessor.getStatus());
//        addressCheckBox.setValue(addressOptionalAttributeProcessor.getStatus());
//
//        super.setValue(value);
//    }
//
//    private HorizontalLayout createOptionalLine(Checkbox checkBox,
//                                                Component component,
//                                                HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> listener){
//        checkBox.addValueChangeListener(listener);
//        return new HorizontalLayout(checkBox, component);
//    }
//
//    @Override
//    protected SaveFormEvent<EditForm<Seller>, Seller> createSaveEvent() {
//        urlOptionalAttributeProcessor.clear(value);
//        descriptionOptionalAttributeProcessor.clear(value);
//        addressOptionalAttributeProcessor.clear(value);
//
//        return new SellerSaveFormEvent(this, value);
//    }
//
//    @Override
//    protected DeleteFormEvent<EditForm<Seller>, Seller> createDeleteEvent() {
//        return new SellerDeleteFormEvent(this, value);
//    }
//
//    @Override
//    protected CloseFormEvent<EditForm<Seller>, Seller> createCloseEvent() {
//        return new SellerCloseFormEvent(this);
//    }
//
//    private void setAddressStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
//        addressOptionalAttributeProcessor.setStatus(event.getValue());
//    }
//
//    private void setDescriptionStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
//        descriptionOptionalAttributeProcessor.setStatus(event.getValue());
//    }
//
//    private void setUrlStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
//        urlOptionalAttributeProcessor.setStatus(event.getValue());
//    }
//
//    public static class SellerSaveFormEvent extends SaveFormEvent<EditForm<Seller>, Seller> {
//        public SellerSaveFormEvent(EditForm<Seller> source, Seller value) {
//            super(source, value);
//        }
//    }
//
//    public static class SellerDeleteFormEvent extends DeleteFormEvent<EditForm<Seller>, Seller> {
//        public SellerDeleteFormEvent(EditForm<Seller> source, Seller value) {
//            super(source, value);
//        }
//    }
//
//    public static class SellerCloseFormEvent extends CloseFormEvent<EditForm<Seller>, Seller> {
//        public SellerCloseFormEvent(EditForm<Seller> source) {
//            super(source);
//        }
//    }
//}
