package kpn.financecontroller.gui.views.payment;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import kpn.financecontroller.data.domains.payment.Currency;
import kpn.financecontroller.data.domains.payment.Measure;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

final public class PaymentForm extends EditForm<Payment> {
    private final ComboBox<Product> product = new ComboBox<>();
    private final ComboBox<Place> place = new ComboBox<>();
    private final TextField amount = new TextField();
    private final ComboBox<Measure> measure = new ComboBox<>();
    private final TextField price = new TextField();
    private final ComboBox<Currency> currency = new ComboBox<>();
    private final DatePicker createdAt = new DatePicker();

    private final Checkbox measureCheckBox = new Checkbox();
    private final Checkbox amountCheckBox = new Checkbox();

    private boolean measurePresent;
    private boolean amountPresent;

    public PaymentForm(List<Product> products,
                       List<Place> places) {
        super(new Binder<>(Payment.class));
        addClassName("payment-form");

        product.setLabel(getTranslation("gui.label.product"));
        place.setLabel(getTranslation("gui.label.place"));
        amount.setLabel(getTranslation("gui.label.amount"));
        measure.setLabel(getTranslation("gui.label.measure"));
        price.setLabel(getTranslation("gui.label.price"));
        currency.setLabel(getTranslation("gui.label.currency"));
        createdAt.setLabel(getTranslation("gui.label.createdAt"));

        binder.forField(amount)
                .withConverter(new StringFloatConverter())
                .bind(Payment::getAmount, Payment::setAmount);

        binder.forField(price)
                .withConverter(new StringFloatConverter())
                .bind(Payment::getPrice, Payment::setPrice);
        binder.bindInstanceFields(this);

        product.setItems(products);
        product.setItemLabelGenerator(Product::getName);

        place.setItems(places);
        place.setItemLabelGenerator(Place::getName);

        measure.setItems(Measure.values());
        currency.setItems(Currency.values());

        add(
                product,
                price,
                currency,
                createOptionalLine(amountCheckBox, amount, this::callOnAmountCheckBoxStateChanging),
                createOptionalLine(measureCheckBox, measure, this::callOnMeasureCheckBoxStateChanging),
                place,
                createdAt,
                createButtonsLayout()
        );
    }

    @Override
    public void setValue(Payment value) {
        if (value != null){
            measurePresent = value.getMeasure() != null;
            amountPresent = value.getAmount() != null;
        } else {
            measurePresent = false;
            amountPresent = false;
        }
        measureCheckBox.setValue(measurePresent);
        amountCheckBox.setValue(amountPresent);

        super.setValue(value);
    }

    private HorizontalLayout createOptionalLine(Checkbox checkBox,
                                                Component component,
                                                HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> listener){
        checkBox.addValueChangeListener(listener);
        return new HorizontalLayout(checkBox, component);
    }

    private void callOnMeasureCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        measurePresent = event.getValue();
    }

    private void callOnAmountCheckBoxStateChanging(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        amountPresent = event.getValue();
    }

    @Override
    protected SaveFormEvent<EditForm<Payment>, Payment> createSaveEvent() {
        if (!measurePresent){
            value.setMeasure(null);
        }
        if (!amountPresent){
            value.setAmount(null);
        }
        return new PaymentSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Payment>, Payment> createDeleteEvent() {
        return new PaymentDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Payment>, Payment> createCloseEvent() {
        return new PaymentCloseFormEvent(this);
    }

    public static class PaymentSaveFormEvent extends SaveFormEvent<EditForm<Payment>, Payment> {
        public PaymentSaveFormEvent(EditForm<Payment> source, Payment value) {
            super(source, value);
        }
    }

    public static class PaymentDeleteFormEvent extends DeleteFormEvent<EditForm<Payment>, Payment> {
        public PaymentDeleteFormEvent(EditForm<Payment> source, Payment value) {
            super(source, value);
        }
    }

    public static class PaymentCloseFormEvent extends CloseFormEvent<EditForm<Payment>, Payment> {
        public PaymentCloseFormEvent(EditForm<Payment> source) {
            super(source);
        }
    }

    private static class StringFloatConverter implements Converter<String, Float>{
        @Override
        public Result<Float> convertToModel(String value, ValueContext context) {
            return Result.<Float>ok(value != null && !value.isEmpty() ? Float.parseFloat(value) : 0.0f);
        }

        @Override
        public String convertToPresentation(Float value, ValueContext context) {
            return value != null ? String.valueOf(value) : "";
        }
    }
}
