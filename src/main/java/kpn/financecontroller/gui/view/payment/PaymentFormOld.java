// TODO: 03.08.2022 del
//package kpn.financecontroller.gui.view.payment;
//
//import com.vaadin.flow.component.AbstractField;
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.HasValue;
//import com.vaadin.flow.component.checkbox.Checkbox;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.datepicker.DatePicker;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.Result;
//import com.vaadin.flow.data.binder.ValueContext;
//import com.vaadin.flow.data.converter.Converter;
//import kpn.financecontroller.data.domains.payment.Currency;
//import kpn.financecontroller.data.domains.payment.Measure;
//import kpn.financecontroller.data.domains.payment.Payment;
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.gui.event.CloseFormEvent;
//import kpn.financecontroller.gui.event.DeleteFormEvent;
//import kpn.financecontroller.gui.event.SaveFormEvent;
//import kpn.financecontroller.gui.status.AttributeProcessor;
//import kpn.financecontroller.gui.status.OptionalAttributeProcessor;
//import kpn.financecontroller.gui.view.EditFormOld;
//
//import java.util.List;
//
//// TODO: 03.08.2022 del
//final public class PaymentFormOld extends EditFormOld<Payment> {
//    private final ComboBox<Product> product = new ComboBox<>();
//    private final ComboBox<Seller> seller = new ComboBox<>();
//    private final TextField amount = new TextField();
//    private final ComboBox<Measure> measure = new ComboBox<>();
//    private final TextField price = new TextField();
//    private final ComboBox<Currency> currency = new ComboBox<>();
//    private final DatePicker createdAt = new DatePicker();
//
//    private final Checkbox measureCheckBox = new Checkbox();
//    private final Checkbox amountCheckBox = new Checkbox();
//    private final Checkbox sellerCheckBox = new Checkbox();
//
//    private final AttributeProcessor<Boolean, Payment> amountOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
//            payment -> {return payment != null && payment.getAmount() != null;},
//            payment -> {payment.setAmount(null);}
//    );
//    private final AttributeProcessor<Boolean, Payment> measureOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
//            payment -> {return payment != null && payment.getMeasure() != null;},
//            payment -> {payment.setMeasure(null);}
//    );
//    private final AttributeProcessor<Boolean, Payment> sellerOptionalAttributeProcessor = new OptionalAttributeProcessor<>(
//            payment -> {return payment != null && payment.getSeller() != null;},
//            payment -> {payment.setSeller(null);}
//    );
//
//    public PaymentFormOld(List<Product> products,
//                          List<Seller> sellers) {
//        super(new Binder<>(Payment.class));
//        addClassName("payment-form");
//
//        product.setLabel(getTranslation("gui.label.product"));
//        seller.setLabel(getTranslation("gui.label.seller"));
//        amount.setLabel(getTranslation("gui.label.amount"));
//        measure.setLabel(getTranslation("gui.label.measure"));
//        price.setLabel(getTranslation("gui.label.price"));
//        currency.setLabel(getTranslation("gui.label.currency"));
//        createdAt.setLabel(getTranslation("gui.label.createdAt"));
//
//        binder.forField(amount)
//                .withConverter(new StringFloatConverter())
//                .bind(Payment::getAmount, Payment::setAmount);
//
//        binder.forField(price)
//                .withConverter(new StringFloatConverter())
//                .bind(Payment::getPrice, Payment::setPrice);
//        binder.bindInstanceFields(this);
//
//        product.setItems(products);
//        product.setItemLabelGenerator(Product::getName);
//
//        seller.setItems(sellers);
//        seller.setItemLabelGenerator(Seller::getName);
//
//        measure.setItems(Measure.values());
//        currency.setItems(Currency.values());
//
//        add(
//                product,
//                price,
//                currency,
//                createOptionalLine(amountCheckBox, amount, this::setAmountStatus),
//                createOptionalLine(measureCheckBox, measure, this::setMeasureStatus),
//                createOptionalLine(sellerCheckBox, seller, this::setSellerStatus),
//                createdAt,
//                createButtonsLayout()
//        );
//    }
//
//    @Override
//    public void setValue(Payment value) {
//        measureOptionalAttributeProcessor.calculate(value);
//        amountOptionalAttributeProcessor.calculate(value);
//        sellerOptionalAttributeProcessor.calculate(value);
//
//        measureCheckBox.setValue(measureOptionalAttributeProcessor.getStatus());
//        amountCheckBox.setValue(amountOptionalAttributeProcessor.getStatus());
//        sellerCheckBox.setValue(sellerOptionalAttributeProcessor.getStatus());
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
//    protected SaveFormEvent<EditFormOld<Payment>, Payment> createSaveEvent() {
//        measureOptionalAttributeProcessor.clear(value);
//        amountOptionalAttributeProcessor.clear(value);
//        sellerOptionalAttributeProcessor.clear(value);
//
//        return new PaymentSaveFormEvent(this, value);
//    }
//
//    @Override
//    protected DeleteFormEvent<EditFormOld<Payment>, Payment> createDeleteEvent() {
//        return new PaymentDeleteFormEvent(this, value);
//    }
//
//    @Override
//    protected CloseFormEvent<EditFormOld<Payment>, Payment> createCloseEvent() {
//        return new PaymentCloseFormEvent(this);
//    }
//
//    public static class PaymentSaveFormEvent extends SaveFormEvent<EditFormOld<Payment>, Payment> {
//        public PaymentSaveFormEvent(EditFormOld<Payment> source, Payment value) {
//            super(source, value);
//        }
//    }
//
//    public static class PaymentDeleteFormEvent extends DeleteFormEvent<EditFormOld<Payment>, Payment> {
//        public PaymentDeleteFormEvent(EditFormOld<Payment> source, Payment value) {
//            super(source, value);
//        }
//    }
//
//    public static class PaymentCloseFormEvent extends CloseFormEvent<EditFormOld<Payment>, Payment> {
//        public PaymentCloseFormEvent(EditFormOld<Payment> source) {
//            super(source);
//        }
//    }
//
//    private static class StringFloatConverter implements Converter<String, Float>{
//        @Override
//        public Result<Float> convertToModel(String value, ValueContext context) {
//            try{
//                return Result.<Float>ok(Float.parseFloat(prepareStrValue(value)));
//            } catch (NumberFormatException ex){
//                return Result.<Float>ok(0.0f);
//            }
//        }
//
//        @Override
//        public String convertToPresentation(Float value, ValueContext context) {
//            return value != null ? String.valueOf(value) : "0.0";
//        }
//
//        private String prepareStrValue(String value) {
//            if (value != null){
//                value = value.replace(",", ".");
//            }
//            return value;
//        }
//    }
//
//    private void setAmountStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
//        amountOptionalAttributeProcessor.setStatus(event.getValue());
//    }
//
//    private void setMeasureStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
//        measureOptionalAttributeProcessor.setStatus(event.getValue());
//    }
//
//    private void setSellerStatus(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
//        sellerOptionalAttributeProcessor.setStatus(event.getValue());
//    }
//}
