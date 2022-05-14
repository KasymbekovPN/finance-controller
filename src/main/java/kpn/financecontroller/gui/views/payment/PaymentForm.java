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
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

final public class PaymentForm extends EditForm<Payment> {
    private final ComboBox<Product> product = new ComboBox<>();
    private final ComboBox<Seller> seller = new ComboBox<>();
    private final TextField amount = new TextField();
    private final ComboBox<Measure> measure = new ComboBox<>();
    private final TextField price = new TextField();
    private final ComboBox<Currency> currency = new ComboBox<>();
    private final DatePicker createdAt = new DatePicker();

    private final Checkbox measureCheckBox = new Checkbox();
    private final Checkbox amountCheckBox = new Checkbox();
    private final Checkbox sellerCheckBox = new Checkbox();

    private final PresenceStatus measurePresenceStatus = new PresenceStatus(
            payment -> {
                return payment != null && payment.getMeasure() != null;
            },
            payment -> {
                payment.setMeasure(null);
            }
    );
    private final PresenceStatus amountPresenceStatus = new PresenceStatus(
            payment -> {
                return payment != null && payment.getAmount() != null;
            },
            payment -> {
                payment.setAmount(null);
            }
    );
    private final PresenceStatus sellerPresenceStatus = new PresenceStatus(
            payment -> {
                return payment != null && payment.getSeller() != null;
            },
            payment -> {
                payment.setSeller(null);
            }
    );

    public PaymentForm(List<Product> products,
                       List<Seller> sellers) {
        super(new Binder<>(Payment.class));
        addClassName("payment-form");

        product.setLabel(getTranslation("gui.label.product"));
        seller.setLabel(getTranslation("gui.label.seller"));
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

        seller.setItems(sellers);
        seller.setItemLabelGenerator(Seller::getName);

        measure.setItems(Measure.values());
        currency.setItems(Currency.values());

        add(
                product,
                price,
                currency,
                createOptionalLine(amountCheckBox, amount, amountPresenceStatus::set),
                createOptionalLine(measureCheckBox, measure, measurePresenceStatus::set),
                createOptionalLine(sellerCheckBox, seller, sellerPresenceStatus::set),
                createdAt,
                createButtonsLayout()
        );
    }

    @Override
    public void setValue(Payment value) {
        measurePresenceStatus.calculate(value);
        amountPresenceStatus.calculate(value);
        sellerPresenceStatus.calculate(value);

        measureCheckBox.setValue(measurePresenceStatus.getStatus());
        amountCheckBox.setValue(amountPresenceStatus.getStatus());
        sellerCheckBox.setValue(sellerPresenceStatus.getStatus());

        super.setValue(value);
    }

    private HorizontalLayout createOptionalLine(Checkbox checkBox,
                                                Component component,
                                                HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> listener){
        checkBox.addValueChangeListener(listener);
        return new HorizontalLayout(checkBox, component);
    }

    @Override
    protected SaveFormEvent<EditForm<Payment>, Payment> createSaveEvent() {
        measurePresenceStatus.clear(value);
        amountPresenceStatus.clear(value);
        sellerPresenceStatus.clear(value);

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

    // TODO: 12.05.2022 del
    @RequiredArgsConstructor
    private static class PresenceStatus{
        private final Function<Payment, Boolean> calculationFunc;
        private final Consumer<Payment> clearFunc;

        @Getter
        private Boolean status = false;

        private void set(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
            status = event.getValue();
        }

        private void calculate(Payment payment){
            status = calculationFunc.apply(payment);
        }

        private void clear(Payment payment){
            if (!status){
                clearFunc.accept(payment);
            }
        }
    }
}
