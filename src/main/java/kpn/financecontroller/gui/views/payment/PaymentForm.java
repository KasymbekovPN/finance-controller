package kpn.financecontroller.gui.views.payment;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.payment.Currency;
import kpn.financecontroller.data.domains.payment.Measure;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class PaymentForm extends EditForm<Payment> {
    private final ComboBox<Product> product = new ComboBox<>();
    private final ComboBox<Building> building = new ComboBox<>();
    private final TextField amount = new TextField();
    private final ComboBox<Measure> measure = new ComboBox<>();
    private final TextField price = new TextField();
    private final ComboBox<Currency> currency = new ComboBox<>();
    private final DatePicker createdAt = new DatePicker();

    public PaymentForm(List<Product> products,
                       List<Building> buildings) {
        super(new Binder<>(Payment.class));
        addClassName("payment-form");

        product.setLabel(getTranslation("gui.product"));
        building.setLabel(getTranslation("gui.building"));
        amount.setLabel(getTranslation("gui.amount"));
        measure.setLabel(getTranslation("gui.measure"));
        price.setLabel(getTranslation("gui.price"));
        currency.setLabel(getTranslation("gui.currency"));
        createdAt.setLabel(getTranslation("gui.createdAt"));

        // TODO: 19.01.2022 create string to float.2 converter 
        binder.forField(amount)
                .withConverter(new Converter<String, Float>() {

                    @Override
                    public Result<Float> convertToModel(String value, ValueContext context) {
                        return Result.<Float>ok(value != null && !value.isEmpty() ? Float.parseFloat(value) : 0.0f);
                    }

                    @Override
                    public String convertToPresentation(Float value, ValueContext context) {
                        return value != null ? String.valueOf(value) : "";
                    }
                })
                .bind(Payment::getAmount, Payment::setAmount);

        binder.forField(price)
                .withConverter(new Converter<String, Float>() {

                    @Override
                    public Result<Float> convertToModel(String value, ValueContext context) {
                        return Result.<Float>ok(value != null && !value.isEmpty() ? Float.parseFloat(value) : 0.0f);
                    }

                    @Override
                    public String convertToPresentation(Float value, ValueContext context) {
                        return value != null ? String.valueOf(value) : "";
                    }
                })
                .bind(Payment::getPrice, Payment::setPrice);

        binder.bindInstanceFields(this);

        product.setItems(products);
        product.setItemLabelGenerator(Product::getName);

        building.setItems(buildings);
        building.setItemLabelGenerator(Building::getFullName);

        measure.setItems(Measure.values());
        currency.setItems(Currency.values());

        add(
                product,
                price,
                currency,
                amount,
                measure,
                building,
                createdAt,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Payment>, Payment> createSaveEvent() {
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
}
