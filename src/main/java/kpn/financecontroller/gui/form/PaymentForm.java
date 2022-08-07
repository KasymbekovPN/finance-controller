package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.event.payment.form.PaymentCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.payment.form.PaymentDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.payment.form.PaymentSaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class PaymentForm extends Form<Payment> {
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

    public PaymentForm(Service<Long, Product, Predicate, Result<List<Product>>> productService,
                       Service<Long, Seller, Predicate, Result<List<Seller>>> sellerService) {
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

        product.setItems(productService.loader().all().getValue());
        product.setItemLabelGenerator(Product::getName);

        seller.setItems(sellerService.loader().all().getValue());
        seller.setItemLabelGenerator(Seller::getName);

        measure.setItems(Measure.values());
        currency.setItems(Currency.values());

        add(
                product,
                price,
                currency,
                new HorizontalLayout(amountCheckBox, amount),
                new HorizontalLayout(measureCheckBox, measure),
                new HorizontalLayout(sellerCheckBox, seller),
                createdAt,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    public void setValue(Payment value) {
        measureCheckBox.setValue(value != null && value.getMeasure() != null);
        amountCheckBox.setValue(value != null && value.getAmount() != null);
        sellerCheckBox.setValue(value != null && value.getSeller() != null);
        super.setValue(value);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        if (!measureCheckBox.getValue()){
            value.setMeasure(null);
        }
        if (!amountCheckBox.getValue()){
            value.setAmount(null);
        }
        if (!sellerCheckBox.getValue()){
            value.setSeller(null);
        }
        return new PaymentSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new PaymentCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new PaymentDeleteButtonOnClickEvent(this, value);
    }

    private static class StringFloatConverter implements Converter<String, Float> {
        @Override
        public com.vaadin.flow.data.binder.Result<Float> convertToModel(String value, ValueContext context) {
            try{
                return com.vaadin.flow.data.binder.Result.<Float>ok(Float.parseFloat(prepareStrValue(value)));
            } catch (NumberFormatException ex){
                return com.vaadin.flow.data.binder.Result.<Float>ok(0.0f);
            }
        }

        @Override
        public String convertToPresentation(Float value, ValueContext context) {
            return value != null ? String.valueOf(value) : "0.0";
        }

        private String prepareStrValue(String value) {
            if (value != null){
                value = value.replace(",", ".");
            }
            return value;
        }
    }
}
